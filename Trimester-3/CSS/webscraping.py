import request
from bs4 import BeautifulSoup

url = "https://yellowpages.in/search/hyderabad/food"

response = requests.get(url)

print(response)