"""
Missing Data Analysis page
"""

import streamlit as st
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from utils.data_loader import load_data

def render_missing_data_page():
    """Render the missing data analysis page"""
    
    st.markdown('<h2 class="section-header">❓ Missing Data Analysis</h2>', unsafe_allow_html=True)
    
    df = load_data()
    if df is None:
        return
    
    # Missing data overview
    missing_data = df.isnull().sum()
    missing_percent = (missing_data / len(df)) * 100
    
    col1, col2 = st.columns(2)
    
    with col1:
        st.subheader("📊 Missing Data Summary")
        missing_df = pd.DataFrame({
            'Column': missing_data.index,
            'Missing_Count': missing_data.values,
            'Missing_Percentage': missing_percent.values
        }).sort_values('Missing_Count', ascending=False)
        
        st.dataframe(missing_df)
    
    with col2:
        st.subheader("📈 Missing Data Visualization")
        fig, ax = plt.subplots(figsize=(10, 6))
        missing_percent.plot(kind='bar', color='coral')
        plt.title('Missing Data Percentage by Column')
        plt.ylabel('Missing Percentage (%)')
        plt.xticks(rotation=45, ha='right')
        plt.tight_layout()
        st.pyplot(fig)
    
    # Missing data patterns
    st.subheader("🔍 Missing Data Patterns")
    
    # Create missing data matrix
    missing_matrix = df.isnull()
    
    # Pattern analysis
    pattern_counts = missing_matrix.value_counts()
    if len(pattern_counts) > 1:
        st.write(f"Found {len(pattern_counts)} different missing data patterns")
        
        # Show top patterns
        top_patterns = pattern_counts.head(10)
        st.write("**Top Missing Data Patterns:**")
        for i, (pattern, count) in enumerate(top_patterns.items()):
            st.write(f"{i+1}. {count} records with pattern: {pattern}")
    
    # Missing vs not missing analysis
    st.subheader("📊 Missing vs Not Missing Analysis")
    
    # For each column with missing data, analyze the impact
    for col in df.columns:
        if df[col].isnull().sum() > 0:
            st.write(f"**Analysis for {col}:**")
            
            # Create comparison groups
            missing_mask = df[col].isnull()
            
            # Compare numerical columns
            numeric_cols = df.select_dtypes(include=[np.number]).columns
            numeric_cols = [c for c in numeric_cols if c != col]
            
            if len(numeric_cols) > 0:
                col1, col2 = st.columns(2)
                
                with col1:
                    comparison_data = []
                    for num_col in numeric_cols[:3]:  # Show first 3 numeric columns
                        missing_mean = df[missing_mask][num_col].mean()
                        not_missing_mean = df[~missing_mask][num_col].mean()
                        comparison_data.append({
                            'Variable': num_col,
                            'Missing_Mean': missing_mean,
                            'Not_Missing_Mean': not_missing_mean,
                            'Difference': missing_mean - not_missing_mean
                        })
                    
                    if comparison_data:
                        comp_df = pd.DataFrame(comparison_data)
                        st.dataframe(comp_df)
                
                with col2:
                    # Visualize the comparison for one key variable (e.g., Defects)
                    if 'Defects' in numeric_cols:
                        fig, ax = plt.subplots(figsize=(8, 5))
                        
                        missing_defects = df[missing_mask]['Defects'].dropna()
                        not_missing_defects = df[~missing_mask]['Defects'].dropna()
                        
                        ax.hist([missing_defects, not_missing_defects], 
                               bins=15, alpha=0.7, label=[f'{col} Missing', f'{col} Not Missing'])
                        ax.set_xlabel('Defects')
                        ax.set_ylabel('Frequency')
                        ax.set_title(f'Defects Distribution: {col} Missing vs Not Missing')
                        ax.legend()
                        st.pyplot(fig)
            
            st.markdown("---")
