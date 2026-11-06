with open(r'backend\src\main\java\in\bloodbridge\service\HospitalService.java', 'r', encoding='utf-8') as f:
    c = f.read()
c = c.replace('.documentName(dto.getDocumentName())\n', '')
c = c.replace('.documentName(dto.getDocumentName())\r\n', '')
with open(r'backend\src\main\java\in\bloodbridge\service\HospitalService.java', 'w', encoding='utf-8') as f:
    f.write(c)

with open(r'backend\src\main\java\in\bloodbridge\exception\GlobalExceptionHandler.java', 'r', encoding='utf-8') as f:
    c = f.read()
old_part = """        var fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> ErrorResponseDto.FieldErrorDto.builder()
                        .field(f.getField())
                        .message(f.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());"""
new_part = """        List<ErrorResponseDto.FieldErrorDto> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> new ErrorResponseDto.FieldErrorDto(f.getField(), f.getDefaultMessage()))
                .collect(Collectors.toList());"""
c = c.replace(old_part.replace('\r\n', '\n'), new_part.replace('\r\n', '\n'))
c = c.replace(old_part, new_part)
with open(r'backend\src\main\java\in\bloodbridge\exception\GlobalExceptionHandler.java', 'w', encoding='utf-8') as f:
    f.write(c)

print("Applied compile fixes")
