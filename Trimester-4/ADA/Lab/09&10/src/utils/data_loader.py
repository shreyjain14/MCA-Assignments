"""
Data loading and preprocessing utilities
"""

import streamlit as st
import pandas as pd
import numpy as np
from sklearn.preprocessing import LabelEncoder
from utils.config import DATA_FILE

@st.cache_data
def load_data():
    """Load and return the production data"""
    try:
        df = pd.read_csv(DATA_FILE)
        return df
    except FileNotFoundError:
        st.error(f"Dataset file '{DATA_FILE}' not found. Please ensure it's in the same directory.")
        return None

def clean_data(df):
    """Clean and preprocess the data"""
    if df is None:
        return None
    
    df_cleaned = df.copy()
    
    # Handle missing categorical values
    categorical_cols = ['Product_Type', 'Machine_Used', 'Shift', 'Operator_ID']
    for col in categorical_cols:
        if col in df_cleaned.columns:
            mode_val = df_cleaned[col].mode().iloc[0] if not df_cleaned[col].mode().empty else 'Unknown'
            df_cleaned.loc[:, col] = df_cleaned[col].fillna(mode_val)
    
    # Handle missing numerical values
    numerical_cols = ['Quantity', 'Downtime_Minutes', 'Defects', 'Estimated_Time', 'Actual_Time_Taken']
    for col in numerical_cols:
        if col in df_cleaned.columns:
            median_val = df_cleaned[col].median()
            df_cleaned.loc[:, col] = df_cleaned[col].fillna(median_val)
    
    return df_cleaned

def prepare_model_data(df):
    """Prepare data for machine learning models"""
    if df is None:
        return None, None, {}
    
    df_model = clean_data(df)
    
    # Remove rows where target variable (Defects) is missing
    df_model = df_model.dropna(subset=['Defects'])
    
    # Encode categorical variables
    label_encoders = {}
    categorical_cols = ['Product_Type', 'Machine_Used', 'Shift', 'Operator_ID']
    
    for col in categorical_cols:
        if col in df_model.columns:
            le = LabelEncoder()
            df_model[f'{col}_encoded'] = le.fit_transform(df_model[col].astype(str))
            label_encoders[col] = le
    
    # Select features for prediction (excluding time-related columns)
    feature_cols = ['Product_Type_encoded', 'Quantity', 'Machine_Used_encoded', 
                   'Shift_encoded', 'Operator_ID_encoded', 'Downtime_Minutes']
    
    # Filter available features
    available_features = [col for col in feature_cols if col in df_model.columns]
    
    if len(available_features) == 0:
        return None, None, {}
    
    X = df_model[available_features]
    y = df_model['Defects']
    
    return X, y, label_encoders

def get_data_summary(df):
    """Get basic data summary statistics"""
    if df is None:
        return {}
    
    return {
        'total_records': len(df),
        'total_columns': len(df.columns),
        'missing_values': df.isnull().sum().sum(),
        'duplicate_records': df.duplicated().sum(),
        'complete_records': len(df.dropna()),
        'data_completeness': (len(df.dropna()) / len(df)) * 100
    }
