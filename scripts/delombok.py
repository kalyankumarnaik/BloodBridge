import os
import re

def process_file(file_path):
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()

    if 'lombok' not in content:
        return

    # Check class name
    class_match = re.search(r'public\s+(class|enum|interface|record)\s+(\w+)', content)
    if not class_match:
        return
    class_type = class_match.group(1)
    class_name = class_match.group(2)

    has_slf4j = '@Slf4j' in content
    has_req_args = '@RequiredArgsConstructor' in content
    has_getter_setter = ('@Getter' in content or '@Setter' in content or '@Data' in content)
    has_builder = '@Builder' in content

    # Clean imports
    content = re.sub(r'import\s+lombok\.[^;]+;\s*\n', '', content)
    content = re.sub(r'@Slf4j\s*\n', '', content)
    content = re.sub(r'@RequiredArgsConstructor\s*\n', '', content)
    content = re.sub(r'@Getter\s*\n', '', content)
    content = re.sub(r'@Setter\s*\n', '', content)
    content = re.sub(r'@Data\s*\n', '', content)
    content = re.sub(r'@NoArgsConstructor\s*\n', '', content)
    content = re.sub(r'@AllArgsConstructor\s*\n', '', content)
    content = re.sub(r'@Builder\.Default\s*\n', '', content)
    content = re.sub(r'@Builder\s*\n', '', content)

    # Insert SLF4J imports if needed
    if has_slf4j:
        if 'import org.slf4j.Logger;' not in content:
            content = re.sub(r'(package\s+[^;]+;\s*\n)', r'\1\nimport org.slf4j.Logger;\nimport org.slf4j.LoggerFactory;\n', content)

    # Find the opening brace of the class
    class_decl_regex = re.compile(r'(public\s+class\s+' + class_name + r'[^{]*\{)')
    match = class_decl_regex.search(content)
    if not match:
        return

    header_end = match.end()
    prefix = content[:header_end]
    body = content[header_end:]

    extra_code = []

    # Insert logger
    if has_slf4j:
        extra_code.append(f'\n    private static final Logger log = LoggerFactory.getLogger({class_name}.class);\n')

    # Parse fields
    # Look for fields inside body: private [final] Type name [= default];
    field_regex = re.compile(r'^\s*private\s+(final\s+)?([A-Za-z0-9_<>,\s?]+)\s+(\w+)(\s*=\s*[^;]+)?\s*;', re.MULTILINE)
    fields = []
    final_fields = []
    for fm in field_regex.finditer(body):
        is_final = bool(fm.group(1))
        ftype = fm.group(2).strip()
        fname = fm.group(3).strip()
        fdefault = fm.group(4)
        if 'static' in ftype:
            continue
        fields.append((fname, ftype, fdefault))
        if is_final:
            final_fields.append((fname, ftype))

    # RequiredArgsConstructor
    if has_req_args and final_fields:
        params = ', '.join([f'{ft} {fn}' for fn, ft in final_fields])
        assigns = '\n'.join([f'        this.{fn} = {fn};' for fn, _ in final_fields])
        extra_code.append(f'\n    public {class_name}({params}) {{\n{assigns}\n    }}\n')

    # If entity or DTO with getters/setters/builder
    if has_getter_setter or has_builder:
        # Default constructor
        no_arg_ctor = f'\n    public {class_name}() {{\n    }}\n'
        # All args constructor
        all_params = ', '.join([f'{ft} {fn}' for fn, ft, _ in fields])
        all_assigns = '\n'.join([f'        this.{fn} = {fn};' for fn, _, _ in fields])
        all_arg_ctor = f'\n    public {class_name}({all_params}) {{\n{all_assigns}\n    }}\n' if fields else ''

        methods = []
        # Getters and Setters
        for fn, ft, _ in fields:
            cap = fn[0].upper() + fn[1:]
            # Getter
            if ft == 'boolean' or ft == 'Boolean':
                methods.append(f'    public {ft} is{cap}() {{\n        return {fn};\n    }}')
                methods.append(f'    public {ft} get{cap}() {{\n        return {fn};\n    }}')
            else:
                methods.append(f'    public {ft} get{cap}() {{\n        return {fn};\n    }}')
            # Setter
            methods.append(f'    public void set{cap}({ft} {fn}) {{\n        this.{fn} = {fn};\n    }}')

        # Builder
        if has_builder and fields:
            b_class = f'    public static class {class_name}Builder {{\n'
            for fn, ft, fdef in fields:
                default_init = fdef if fdef else ''
                b_class += f'        private {ft} {fn}{default_init};\n'
            b_class += f'\n        public {class_name}Builder() {{}}\n\n'
            for fn, ft, _ in fields:
                b_class += f'        public {class_name}Builder {fn}({ft} {fn}) {{\n            this.{fn} = {fn};\n            return this;\n        }}\n\n'
            args_call = ', '.join([f'this.{fn}' for fn, _, _ in fields])
            b_class += f'        public {class_name} build() {{\n            return new {class_name}({args_call});\n        }}\n    }}\n\n'
            b_class += f'    public static {class_name}Builder builder() {{\n        return new {class_name}Builder();\n    }}\n'

            methods.append(b_class)

        # Append before last closing brace
        last_brace = body.rfind('}')
        if last_brace != -1:
            body_before = body[:last_brace]
            body_after = body[last_brace:]
            gen_code = '\n' + no_arg_ctor + all_arg_ctor + '\n'.join(methods) + '\n'
            body = body_before + gen_code + body_after

    new_content = prefix + ''.join(extra_code) + body
    with open(file_path, 'w', encoding='utf-8') as f:
        f.write(new_content)
    print(f"Processed: {file_path}")

base_dir = r"backend\src\main\java\in\bloodbridge"
for root, dirs, files in os.walk(base_dir):
    for f in files:
        if f.endswith('.java'):
            process_file(os.path.join(root, f))
