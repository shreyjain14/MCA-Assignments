"""
Overview page for the Production Time Analysis Dashboard
"""

import streamlit as st
import pandas as pd
from utils.data_loader import load_data, get_data_summary

def render_overview_page():
    """Render the overview page"""
    
    st.markdown('<h1 class="main-header">🏭 Production Time Analysis Dashboard</h1>', unsafe_allow_html=True)
    
    df = load_data()
    if df is None:
        return
    
    st.markdown('<h2 class="section-header">📊 Data Overview</h2>', unsafe_allow_html=True)
    
    # Get data summary
    summary = get_data_summary(df)
    
    # Basic metrics
    col1, col2, col3, col4 = st.columns(4)
    with col1:
        st.metric("Total Records", summary['total_records'])
    with col2:
        st.metric("Total Columns", summary['total_columns'])
    with col3:
        st.metric("Missing Values", summary['missing_values'])
    with col4:
        st.metric("Duplicate Records", summary['duplicate_records'])
    
    # Dataset sample
    st.subheader("📋 Dataset Sample")
    st.dataframe(df.head(10))
    
    # Data types and info
    col1, col2 = st.columns(2)
    
    with col1:
        st.subheader("📈 Data Types")
        data_types = pd.DataFrame({
            'Column': df.columns,
            'Data Type': df.dtypes.astype(str),
            'Non-Null Count': df.count().values,
            'Null Count': df.isnull().sum().values
        })
        st.dataframe(data_types)
    
    with col2:
        st.subheader("📊 Basic Statistics")
        st.dataframe(df.describe())
    
        # Project Team and Work Done Table
    st.markdown('<h2 class="section-header">👥 Project Team & Work Distribution</h2>', unsafe_allow_html=True)
    
    # Create detailed team information with separate enrollment numbers
    team_data = {
        'Name': [
            'Shrey Jain',
            'Ayur Dekate', 
            'Ajil A Varghese',
            'Anson Thomas'
        ],
        'Enrollment Number': [
            '2447249',
            '2447114',
            '2447204',
            '2447111'
        ],
        'Primary Role': [
            'Data Visualization and Presentation',
            'Data Cleaning and Preprocessing',
            'Data Analysis and Recommendation',
            'Data Collection and Visualization'
        ],
        'Description': [
            'Crafts clear, accurate, and engaging visuals and narratives from analysis to enable fast, confident decision-making.',
            'Builds reliable, analysis‑ready datasets by fixing quality issues, standardizing structures, and engineering features in reproducible pipelines.',
            'Extracts insights through exploratory and statistical analysis and delivers actionable, evidence‑backed recommendations with defined impact metrics.',
            'Designs and runs ethical data collection workflows and produces early visual summaries to validate data quality and surface initial signals.'
        ]
    }
    
    # Display detailed team contributions with proper formatting
    st.markdown("### 🎯 Detailed Team Contributions")
    
    for i, name in enumerate(team_data['Name']):
        enrollment = team_data['Enrollment Number'][i]
        role = team_data['Primary Role'][i]
        description = team_data['Description'][i]
        
        with st.expander(f"📋 {name} ({enrollment})", expanded=True):
            st.markdown(f"**Primary Role:** {role}")
            st.markdown(f"**Overview:** {description}")
            st.markdown("**Key Responsibilities:**")
            
            # Create detailed bullet points based on each person's role
            if "Shrey Jain" in name:
                st.markdown("""
**Advanced Data Visualization:**
- Created comprehensive interactive dashboards using Streamlit, Plotly, and Matplotlib
- Developed correlation matrices, distribution plots, and missing data analysis charts
- Designed user-friendly navigation systems and responsive layouts for optimal user experience

**Presentation Design:**
- Crafted compelling visual narratives that translate complex data insights into actionable business intelligence
- Created professional documentation for visualization components and user guides
- Implemented efficient data visualization techniques to ensure fast rendering and smooth interactions

**Quality Assurance:**
- Conducted thorough testing of visual components to ensure accuracy and reliability
- Optimized dashboard performance for seamless user experience
- Maintained consistent design standards across all visual elements
                """)
            elif "Ayur Dekate" in name:
                st.markdown("""
**Data Quality Management:**
- Implemented comprehensive data validation checks and quality metrics evaluation
- Developed sophisticated imputation strategies for different data types and patterns
- Established consistent data formats, naming conventions, and structural standards

**Pipeline Development:**
- Built automated, reproducible data preprocessing workflows using Python and pandas
- Created derived variables and transformed existing features to enhance model performance
- Merged and consolidated data from multiple sources while maintaining data integrity

**Performance Monitoring:**
- Implemented data quality monitoring systems to track preprocessing effectiveness
- Established data validation checkpoints throughout the processing pipeline
- Documented all preprocessing steps for reproducibility and maintenance
                """)
            elif "Ajil A Varghese" in name:
                st.markdown("""
**Statistical Analysis:**
- Conducted comprehensive exploratory data analysis to uncover patterns and relationships
- Performed rigorous statistical tests to validate findings and ensure scientific rigor
- Developed and validated machine learning models for defect prediction and quality control

**Business Intelligence:**
- Translated statistical findings into actionable business recommendations with clear ROI metrics
- Identified key risk factors and developed mitigation strategies based on data insights
- Established KPIs and success metrics to measure the effectiveness of recommendations

**Stakeholder Communication:**
- Presented findings to technical and non-technical audiences with clear explanations
- Created executive summaries and technical reports for different stakeholder groups
- Facilitated data-driven decision making through evidence-backed recommendations
                """)
            elif "Anson Thomas" in name:
                st.markdown("""
**Data Collection Strategy:**
- Designed comprehensive data gathering workflows ensuring ethical and legal compliance
- Established data source reliability assessment and verification procedures
- Built automated data collection pipelines to ensure consistent and timely data acquisition

**Quality Visualization:**
- Performed initial data exploration to identify quality issues and potential insights
- Created preliminary charts and graphs to assess data completeness and accuracy
- Developed early warning systems to identify anomalies and patterns in incoming data

**Compliance Management:**
- Ensured all data collection activities meet privacy and regulatory requirements
- Implemented data governance policies and procedures
- Maintained audit trails for all data collection and processing activities
                """)
    
    
    # Display the table with custom styling
    st.markdown("""
    <style>
    .team-table {
        margin: 20px 0;
    }
    .team-table table {
        width: 100%;
        border-collapse: collapse;
    }
    .team-table th {
        background-color: #1f77b4;
        color: white;
        padding: 12px;
        text-align: left;
        border: 1px solid #ddd;
    }
    .team-table td {
        padding: 10px;
        border: 1px solid #ddd;
        background-color: #f9f9f9;
    }
    .team-table tr:nth-child(even) td {
        background-color: #f1f1f1;
    }
    </style>
    """, unsafe_allow_html=True)
    
