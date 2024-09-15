import pandas as pd
import matplotlib.pyplot as plt

# Load the datasets
gdp_df = pd.read_csv('data/gdp.csv')
covid_df = pd.read_csv('data/covid.csv')

print("GDP Dataset:")
print(gdp_df)
print("\nCOVID-19 Dataset:")
print(covid_df)

# Convert month column to datetime
covid_df['month'] = pd.to_datetime(covid_df['month'])

# Add active_cases column
covid_df['active_cases'] = covid_df['confirmed_cases'] - covid_df['recovered']

# Aggregate COVID-19 data by country and quarter
covid_df['quarter'] = covid_df['month'].dt.to_period('Q').astype(str)
covid_agg = covid_df.groupby(['country', 'quarter']).agg({
    'confirmed_cases': 'sum',
    'deaths': 'sum',
    'active_cases': 'sum'
}).reset_index()

print("\nAggregated COVID-19 Dataset:")
print(covid_agg)

# Merge the datasets
merged_df = pd.merge(gdp_df, covid_agg, on=['country', 'quarter'], how='outer')

print("\nMerged DataFrame:")
print(merged_df)

# Check for any missing values
print("\nMissing values in the merged DataFrame:")
print(merged_df.isnull().sum())

# If there are matching rows, proceed with the analysis
if not merged_df.empty:
    # Identify country with highest overall GDP growth
    highest_gdp_growth = merged_df.groupby('country')['gdp_growth'].sum().idxmax()
    print(f"\nCountry with highest overall GDP growth: {highest_gdp_growth}")

    # Determine country with highest total confirmed cases and deaths
    highest_cases = merged_df.groupby('country')['confirmed_cases'].sum().idxmax()
    highest_deaths = merged_df.groupby('country')['deaths'].sum().idxmax()
    print(f"Country with highest total confirmed cases: {highest_cases}")
    print(f"Country with highest total deaths: {highest_deaths}")

    # Visualization
    plt.figure(figsize=(12, 6))
    for country in merged_df['country'].unique():
        country_data = merged_df[merged_df['country'] == country]
        plt.plot(country_data['quarter'], country_data['gdp_growth'], label=country)

    plt.title('GDP Growth by Country and Quarter')
    plt.xlabel('Quarter')
    plt.ylabel('GDP Growth')
    plt.legend()
    plt.xticks(rotation=45)
    plt.tight_layout()
    plt.show()

    # Conclusion
    print("\nConclusion:")
    print(f"Based on the analysis, we can observe that {highest_gdp_growth} experienced the highest overall GDP growth during the pandemic period.")
    print(f"However, {highest_cases} had the highest number of confirmed COVID-19 cases, and {highest_deaths} suffered the most deaths.")
    print("There doesn't seem to be a clear correlation between COVID-19 cases and economic growth across all countries.")
    print("Factors such as government policies, healthcare systems, and economic structures likely played significant roles in determining how each country fared during the pandemic.")
else:
    print("\nNo matching data found between GDP and COVID-19 datasets.")
    print("Please check if the 'country' and 'quarter' values match in both datasets.")