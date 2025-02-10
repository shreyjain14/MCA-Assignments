import pandas as pd
from sklearn.impute import SimpleImputer
from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import train_test_split

def main():
    # Load dataset
    df = pd.read_csv('data.csv')
    print("Initial shape:", df.shape)

    # Data Cleaning: Show missing counts, impute numerical columns, drop duplicates
    print("Missing values per column:\n", df.isnull().sum())
    num_cols = df.select_dtypes(include=['int64', 'float64']).columns
    imputer = SimpleImputer(strategy='mean')
    df[num_cols] = imputer.fit_transform(df[num_cols])
    df = df.drop_duplicates()
    print("Shape after cleaning:", df.shape)

    # Data Transformation: One-hot encode categoricals and scale numerical features
    cat_cols = df.select_dtypes(include=['object']).columns
    df_encoded = pd.get_dummies(df, columns=cat_cols)
    print("Shape after encoding:", df_encoded.shape)
    scaler = StandardScaler()
    df_encoded[num_cols] = scaler.fit_transform(df_encoded[num_cols])
    print("Sample after scaling:\n", df_encoded.head())

    # Data Reduction: Split into features and target
    if 'target' in df_encoded.columns:
        X = df_encoded.drop('target', axis=1)
        y = df_encoded['target']
        X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
        print("Train shape:", X_train.shape)
        print("Test shape:", X_test.shape)
    else:
        print("No target found. Features shape:", df_encoded.shape)

if __name__ == "__main__":
    main()
