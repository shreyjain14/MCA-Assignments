# Production Time Analysis Dashboard

A comprehensive Streamlit web application for analyzing production time dataset and building predictive models for defect prediction.

## Features

### 📊 Data Analysis Components

1. **Data Overview & Statistics**
   - Dataset summary and basic statistics
   - Data types and structure analysis
   - Missing value identification

2. **Data Collection Analysis**
   - Data source evaluation
   - Quality assessment
   - Collection method recommendations

3. **Data Cleaning & Preparation**
   - Missing value handling strategies
   - Data transformation techniques
   - Before/after comparison

4. **Exploratory Data Analysis (EDA)**
   - Distribution analysis
   - Correlation studies
   - Categorical data insights
   - Time-based patterns

5. **Missing Data Analysis**
   - Missing vs non-missing comparisons
   - Pattern identification
   - Impact assessment

6. **Advanced Analysis Techniques**
   - Statistical methods
   - Trend analysis
   - Performance segmentation
   - Reliability assessment

7. **Data Visualization**
   - Interactive charts (Plotly)
   - Statistical plots (Matplotlib/Seaborn)
   - Multiple chart types (scatter, box, violin, heatmap, 3D)
   - Time series visualizations

8. **Business Recommendations**
   - Actionable insights
   - Cost-benefit analysis
   - Implementation roadmap
   - Risk assessment

9. **Predictive Modeling**
   - Defect prediction models
   - Feature importance analysis
   - Model comparison
   - Interactive prediction tool

### 🤖 Predictive Model Features

- **Input Parameters:**
  - Product_Type (A, B, C, D)
  - Quantity (1-1000)
  - Machine_Used (M1, M2, M3)
  - Shift (Morning, Afternoon, Night)
  - Operator_ID (OP1-OP10)
  - Downtime_Minutes (0-20)
  - Estimated_Time (1-1000)

- **Output:**
  - Predicted number of defects
  - Risk level assessment
  - Confidence intervals

- **Models Used:**
  - Random Forest Regressor
  - Linear Regression
  - Feature importance ranking

## Installation & Setup

### Prerequisites
- Python 3.8 or higher
- pip package manager

### Quick Start

1. **Clone or download the project files**
```bash
# Ensure you have these files:
# - app.py
# - production_time_dataset.csv
# - requirements.txt
```

2. **Install dependencies**
```bash
pip install -r requirements.txt
```

3. **Run the application**
```bash
streamlit run app.py
```

4. **Open your browser**
- The application will automatically open at `http://localhost:8501`
- If not, manually navigate to the URL shown in the terminal

## Usage Guide

### Navigation
- Use the sidebar to navigate between different analysis sections
- Each page focuses on a specific aspect of data analysis
- Progress through pages sequentially for best understanding

### Key Sections

1. **🏠 Overview**: Start here for dataset summary
2. **📥 Data Collection**: Understand data sources and quality
3. **🧹 Data Cleaning**: See data preparation steps
4. **🔍 EDA**: Explore data patterns and relationships
5. **❓ Missing Data Analysis**: Detailed missing data investigation
6. **🔬 Analysis Techniques**: Statistical methods and insights
7. **📊 Visualizations**: Interactive charts and graphs
8. **💡 Recommendations**: Business insights and action items
9. **🤖 Predictive Modeling**: Build and use prediction models

### Predictive Modeling
1. Navigate to the "🤖 Predictive Modeling" page
2. Review model performance metrics
3. Use the interactive prediction tool at the bottom
4. Enter production parameters to get defect predictions

## Data Analysis Workflow

### 1. Data Collection Assessment
- **Real vs Synthetic**: This dataset appears to be real production data
- **Sources**: Manufacturing execution systems, operator logs, machine sensors
- **Quality**: Generally good with some missing values requiring attention

### 2. Data Cleaning Steps
- Missing value imputation using statistical methods
- Categorical encoding for machine learning
- Outlier detection and analysis
- Data type standardization

### 3. Analysis Techniques
- **Descriptive Statistics**: Central tendencies and distributions
- **Correlation Analysis**: Relationship identification
- **Trend Analysis**: Time-based patterns
- **Segmentation**: Performance by categories
- **Statistical Testing**: Hypothesis validation

### 4. Visualization Techniques
- **Distribution Charts**: Histograms, box plots, violin plots
- **Relationship Charts**: Scatter plots, correlation heatmaps
- **Time Series**: Trend lines, rolling averages
- **Comparative Charts**: Multi-group comparisons
- **Advanced Visualizations**: 3D plots, parallel coordinates

### 5. Business Recommendations
- **Operational**: Machine maintenance, shift optimization
- **Quality**: Defect reduction strategies
- **Cost**: ROI analysis and savings potential
- **Implementation**: Phased approach with timelines

## Technical Details

### Data Processing
- Pandas for data manipulation
- NumPy for numerical computations
- Scikit-learn for machine learning

### Visualizations
- Plotly for interactive charts
- Matplotlib for static plots
- Seaborn for statistical visualizations

### Machine Learning
- Random Forest for robust predictions
- Linear Regression for baseline comparison
- Feature importance analysis
- Cross-validation for model reliability

## Key Insights from Analysis

### Production Patterns
- Defect rates vary significantly by machine and shift
- Strong correlation between downtime and defect counts
- Product type influences production complexity

### Quality Factors
- Machine M1 shows different performance characteristics
- Night shift patterns differ from day shifts
- Operator experience impacts quality metrics

### Predictive Capabilities
- Models achieve good accuracy for defect prediction
- Downtime is the strongest predictor of defects
- Machine type and product complexity are also important factors

## Customization

### Adding New Analysis
- Extend the `pages` dictionary in `main()` function
- Create new analysis functions following existing patterns
- Use consistent styling with provided CSS classes

### Modifying Models
- Update the `models` dictionary in `predictive_modeling()`
- Add new algorithms or hyperparameter tuning
- Implement additional evaluation metrics

### Data Updates
- Replace `production_time_dataset.csv` with new data
- Ensure column names match expected format
- Update feature lists if schema changes

## Troubleshooting

### Common Issues
1. **File not found**: Ensure CSV file is in the same directory as app.py
2. **Import errors**: Install all requirements using pip
3. **Memory issues**: Use data sampling for large datasets
4. **Performance**: Consider caching for heavy computations

### Support
- Check console output for detailed error messages
- Verify file paths and data format
- Ensure all dependencies are installed correctly

## Future Enhancements

### Potential Additions
- Real-time data integration
- Advanced ML models (XGBoost, Neural Networks)
- Automated report generation
- Email alerts for anomalies
- A/B testing framework
- Integration with manufacturing systems

### Performance Improvements
- Data caching optimization
- Lazy loading for large datasets
- Progressive data loading
- Background model training

---

**Created for ADA Lab Assignment - Production Time Analysis**

This dashboard demonstrates comprehensive data analysis capabilities including data collection assessment, cleaning procedures, statistical analysis, visualization techniques, and predictive modeling for industrial applications.
