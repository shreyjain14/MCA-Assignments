import re


# Example 1: Find all matches
text = "Hello, world! This is a sample text."
pattern = r"\b\w{5}\b"  # Matches words with exactly 5 characters

matches = re.findall(pattern, text)
print("Matches:", matches)


# Example 2: Search for a pattern
text = "The quick brown fox jumps over the lazy dog."
pattern = r"\bfox\b"  # Searches for the word "fox"

match = re.search(pattern, text)
if match:
    print("Pattern found at index", match.start())
else:
    print("Pattern not found")


# Example 3: Replace matches
text = "Hello, world! This is a sample text."
pattern = r"\bworld\b"  # Matches the word "world"

new_text = re.sub(pattern, "universe", text)
print("New text:", new_text)


# Example 4: Splitting a string
text = "Hello, world! This is a sample text."
pattern = r"\W+"  # Matches one or more non-word characters

split_text = re.split(pattern, text)
print("Split text:", split_text)


# Example 5: Using groups
text = "John Doe, 30 years old"
pattern = r"(\w+) (\w+), (\d+) years old"

match = re.search(pattern, text)
if match:
    print("Full match:", match.group(0))
    print("First name:", match.group(1))
    print("Last name:", match.group(2))
    print("Age:", match.group(3))


# Example 6: Using named groups
text = "John Doe, 30 years old"
pattern = r"(?P<first_name>\w+) (?P<last_name>\w+), (?P<age>\d+) years old"

match = re.search(pattern, text)
if match:
    print("Full match:", match.group(0))
    print("First name:", match.group("first_name"))
    print("Last name:", match.group("last_name"))
    print("Age:", match.group("age"))


# Example 7: Using flags
text = "Hello, world! This is a sample text."
pattern = r"\bhello\b"  # Matches the word "hello"

match = re.search(pattern, text, re.IGNORECASE)
if match:
    print("Pattern found at index", match.start())
else:
    print("Pattern not found")
