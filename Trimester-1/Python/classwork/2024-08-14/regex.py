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