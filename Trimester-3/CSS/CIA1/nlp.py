import nltk
nltk.download('punkt_tab') 

from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords

nltk.download('punkt')
nltk.download('stopwords')

text = "Natural Language Processing is amazing! It helps computers understand human language."

tokens = word_tokenize(text)
filtered_tokens = [word for word in tokens if word.lower() not in stopwords.words('english')]

print("Original Tokens:", tokens)
print("Filtered Tokens (without stopwords):", filtered_tokens)
