with open(r'backend\src\main\java\in\bloodbridge\exception\GlobalExceptionHandler.java', 'r', encoding='utf-8') as f:
    c = f.read()
c = c.replace('import java.util.UUID;', 'import java.util.List;\nimport java.util.UUID;')
with open(r'backend\src\main\java\in\bloodbridge\exception\GlobalExceptionHandler.java', 'w', encoding='utf-8') as f:
    f.write(c)
print('Added import java.util.List;')
