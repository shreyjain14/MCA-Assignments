"""
Data Cleaning and Preparation page
"""

import streamlit as st
import numpy as np
from utils.data_loader import load_data, clean_data

def render_data_cleaning_page():
    """Render the data cleaning and preparation page"""
    
    st.markdown('<h2 class="section-header">🧹 Data Cleaning and Preparation</h2>', unsafe_allow_html=True)
    
    df = load_data()
    if df is None:
        return
    
    # Before cleaning statistics
    st.subheader("📋 Data Quality Issues Identified")
    
    col1, col2 = st.columns(2)
    
    with col1:
        st.markdown("**Missing Values by Column:**")
        missing_stats = df.isnull().sum()
        for col, missing in missing_stats.items():
            if missing > 0:
                st.write(f"• {col}: {missing} missing values ({missing/len(df)*100:.1f}%)")
    
    with col2:
        st.markdown("**Data Quality Issues:**")
        issues = []
        
        # Check for outliers in numerical columns
        numeric_cols = df.select_dtypes(include=[np.number]).columns
        for col in numeric_cols:
            if col in df.columns:
                Q1 = df[col].quantile(0.25)
                Q3 = df[col].quantile(0.75)
                IQR = Q3 - Q1
                outliers = len(df[(df[col] < (Q1 - 1.5 * IQR)) | (df[col] > (Q3 + 1.5 * IQR))])
                if outliers > 0:
                    issues.append(f"• {col}: {outliers} potential outliers")
        
        for issue in issues[:5]:  # Show first 5 issues
            st.write(issue)
    
    # Cleaning steps demonstration
    st.subheader("🔧 Data Cleaning Steps")
    
    # Create cleaned dataset
    df_cleaned = clean_data(df)
    
    # Display cleaning steps
    cleaning_steps = [
        "✅ Filled missing categorical values with mode",
        "✅ Filled missing numerical values with median",
        "✅ Standardized data types",
        "✅ Handled outliers appropriately",
        "✅ Validated data consistency"
    ]
    
    for step in cleaning_steps:
        st.write(step)
    
    # Before and after comparison
    col1, col2 = st.columns(2)
    
    with col1:
        st.subheader("📊 Before Cleaning")
        st.metric("Missing Values", df.isnull().sum().sum())
        st.metric("Complete Records", len(df.dropna()))
        st.metric("Data Completeness", f"{(len(df.dropna())/len(df)*100):.1f}%")
    
    with col2:
        st.subheader("✨ After Cleaning")
        st.metric("Missing Values", df_cleaned.isnull().sum().sum())
        st.metric("Complete Records", len(df_cleaned.dropna()))
        st.metric("Data Completeness", f"{(len(df_cleaned.dropna())/len(df_cleaned)*100):.1f}%")
    
    # Data transformations
    st.subheader("🔄 Data Transformations")
    
    transformations = [
        "✅ Standardized product type codes (A, B, C, D)",
        "✅ Normalized machine identifiers (M1, M2, M3)",
        "✅ Standardized shift categories (Morning, Afternoon, Night)",
        "✅ Calculated efficiency metrics (Actual vs Estimated time)",
        "✅ Created defect rate categories",
        "✅ Identified outliers for further investigation"
    ]
    
    for transformation in transformations:
        st.write(transformation)
