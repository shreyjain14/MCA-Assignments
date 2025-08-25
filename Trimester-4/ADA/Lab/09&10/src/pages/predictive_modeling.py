"""
Predictive Modeling page
"""

import streamlit as st
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestRegressor, GradientBoostingRegressor
from sklearn.linear_model import LinearRegression, Ridge
from sklearn.metrics import mean_squared_error, r2_score, mean_absolute_error
from utils.data_loader import load_data, prepare_model_data

def render_predictive_modeling_page():
    """Render the predictive modeling page"""
    
    st.markdown('<h2 class="section-header">🤖 Predictive Modeling for Defects</h2>', unsafe_allow_html=True)
    
    df = load_data()
    if df is None:
        return
    
    # Prepare data for modeling
    st.subheader("🔄 Data Preparation for Modeling")
    
    X, y, label_encoders = prepare_model_data(df)
    
    if X is None:
        st.error("No suitable features available for modeling")
        return
    
    st.write("✅ Data cleaning completed")
    st.write("✅ Missing values handled")
    st.write("✅ Feature engineering completed")
    st.write(f"✅ Features selected: {', '.join(X.columns)}")
    st.write(f"✅ Dataset size: {len(X)} samples")
    
    # Split the data
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
    
    # Model training and evaluation with enhanced models
    st.subheader("🎯 Model Training & Evaluation")
    
    models = {
        'Random Forest': RandomForestRegressor(n_estimators=100, random_state=42),
        'Gradient Boosting': GradientBoostingRegressor(n_estimators=100, random_state=42),
        'Linear Regression': LinearRegression(),
        'Ridge Regression': Ridge(alpha=1.0)
    }
    
    model_results = {}
    
    # Create a 2x2 grid for model results
    col1, col2 = st.columns(2)
    col3, col4 = st.columns(2)
    cols = [col1, col2, col3, col4]
    
    for i, (name, model) in enumerate(models.items()):
        # Train model
        model.fit(X_train, y_train)
        
        # Make predictions
        y_pred = model.predict(X_test)
        
        # Calculate metrics
        mse = mean_squared_error(y_test, y_pred)
        rmse = np.sqrt(mse)
        mae = mean_absolute_error(y_test, y_pred)
        r2 = r2_score(y_test, y_pred)
        
        model_results[name] = {
            'MSE': mse,
            'RMSE': rmse,
            'MAE': mae,
            'R²': r2,
            'Model': model
        }
        
        # Display results
        with cols[i]:
            st.markdown(f"**{name}:**")
            st.metric("RMSE", f"{rmse:.3f}")
            st.metric("MAE", f"{mae:.3f}")
            st.metric("R² Score", f"{r2:.3f}")
    
    # Model comparison
    st.subheader("📊 Model Comparison")
    
    comparison_df = pd.DataFrame(model_results).T
    st.dataframe(comparison_df[['RMSE', 'MAE', 'R²']])
    
    # Feature importance analysis
    st.subheader("🔍 Feature Importance Analysis")
    
    # Get the best tree-based model for feature importance
    tree_models = ['Random Forest', 'Gradient Boosting']
    best_tree_model_name = min([name for name in tree_models if name in model_results], 
                              key=lambda k: model_results[k]['RMSE'])
    best_tree_model = model_results[best_tree_model_name]['Model']
    
    feature_importance = pd.DataFrame({
        'Feature': X.columns,
        'Importance': best_tree_model.feature_importances_
    }).sort_values('Importance', ascending=False)
    
    col1, col2 = st.columns(2)
    
    with col1:
        st.markdown(f"**{best_tree_model_name} Feature Importance:**")
        st.dataframe(feature_importance)
    
    with col2:
        fig, ax = plt.subplots(figsize=(10, 8))
        sns.barplot(data=feature_importance.head(10), x='Importance', y='Feature')
        plt.title(f'Top 10 Features - {best_tree_model_name}')
        plt.tight_layout()
        st.pyplot(fig)
    
    # Production parameters analysis
    st.subheader("🏭 Production Parameters Impact on Defects")
    
    fig, ((ax1, ax2), (ax3, ax4)) = plt.subplots(2, 2, figsize=(15, 10))
    
    # Quantity vs defects
    ax1.scatter(df['Quantity'], df['Defects'], alpha=0.6)
    ax1.set_xlabel('Quantity')
    ax1.set_ylabel('Defects')
    ax1.set_title('Quantity vs Defects')
    
    # Downtime vs defects
    ax2.scatter(df['Downtime_Minutes'], df['Defects'], alpha=0.6)
    ax2.set_xlabel('Downtime (minutes)')
    ax2.set_ylabel('Defects')
    ax2.set_title('Downtime vs Defects')
    
    # Product type vs defects boxplot
    if 'Product_Type' in df.columns:
        sns.boxplot(data=df, x='Product_Type', y='Defects', ax=ax3)
        ax3.set_xlabel('Product Type')
        ax3.set_ylabel('Defects')
        ax3.set_title('Defects by Product Type')
    
    # Machine vs defects boxplot
    if 'Machine_Used' in df.columns:
        sns.boxplot(data=df, x='Machine_Used', y='Defects', ax=ax4)
        ax4.set_xlabel('Machine')
        ax4.set_ylabel('Defects')
        ax4.set_title('Defects by Machine')
    
    plt.tight_layout()
    st.pyplot(fig)
    
    # Prediction visualization
    st.subheader("📈 Prediction vs Actual")
    
    best_model_name = min(model_results.keys(), key=lambda k: model_results[k]['RMSE'])
    best_model = model_results[best_model_name]['Model']
    y_pred_best = best_model.predict(X_test)
    
    fig, ax = plt.subplots(figsize=(10, 6))
    ax.scatter(y_test, y_pred_best, alpha=0.6)
    ax.plot([y_test.min(), y_test.max()], [y_test.min(), y_test.max()], 'r--', lw=2)
    ax.set_xlabel('Actual Defects')
    ax.set_ylabel('Predicted Defects')
    ax.set_title(f'Actual vs Predicted Defects ({best_model_name})')
    correlation = np.corrcoef(y_test, y_pred_best)[0, 1]
    ax.text(0.05, 0.95, f'Correlation: {correlation:.3f}', transform=ax.transAxes, 
            bbox=dict(boxstyle='round', facecolor='wheat', alpha=0.8))
    st.pyplot(fig)
    
    # Interactive prediction tool
    st.subheader("🔮 Production Defect Prediction Tool")
    
    st.write("Enter production parameters to predict defects:")
    
    col1, col2, col3 = st.columns(3)
    
    with col1:
        product_type = st.selectbox("Product Type", ['A', 'B', 'C', 'D'])
        quantity = st.number_input("Quantity", min_value=1, max_value=1000, value=500)
    
    with col2:
        machine = st.selectbox("Machine", ['M1', 'M2', 'M3'])
        shift = st.selectbox("Shift", ['Morning', 'Afternoon', 'Night'])
    
    with col3:
        operator = st.selectbox("Operator ID", [f'OP{i}' for i in range(1, 11)])
        downtime = st.number_input("Downtime (minutes)", min_value=0, max_value=20, value=5)
    
    if st.button("🎯 Predict Defects", use_container_width=True):
        # Prepare input data
        input_data = {}
        available_features = list(X.columns)
        
        # Encode categorical variables
        if 'Product_Type_encoded' in available_features:
            input_data['Product_Type_encoded'] = label_encoders['Product_Type'].transform([product_type])[0]
        if 'Machine_Used_encoded' in available_features:
            input_data['Machine_Used_encoded'] = label_encoders['Machine_Used'].transform([machine])[0]
        if 'Shift_encoded' in available_features:
            input_data['Shift_encoded'] = label_encoders['Shift'].transform([shift])[0]
        if 'Operator_ID_encoded' in available_features:
            input_data['Operator_ID_encoded'] = label_encoders['Operator_ID'].transform([operator])[0]
        
        # Add numerical features
        if 'Quantity' in available_features:
            input_data['Quantity'] = quantity
        if 'Downtime_Minutes' in available_features:
            input_data['Downtime_Minutes'] = downtime
        
        # Create input array
        input_array = np.array([input_data[col] for col in available_features]).reshape(1, -1)
        
        # Make prediction with best model
        prediction = best_model.predict(input_array)[0]
        
        # Display results
        col1, col2, col3 = st.columns(3)
        
        with col1:
            st.metric("🎯 Predicted Defects", f"{prediction:.1f}")
            
        with col2:
            if prediction < 2:
                risk_level = "LOW"
                risk_color = "success"
            elif prediction < 5:
                risk_level = "MEDIUM"
                risk_color = "warning"
            else:
                risk_level = "HIGH"
                risk_color = "error"
            st.metric("🚦 Risk Level", risk_level)
            
        with col3:
            confidence = best_model.score(X_test, y_test)
            st.metric("📊 Model Confidence", f"{confidence:.2f}")
        
        # Production insights
        st.subheader("🔍 Production Insights")
        
        insight_col1, insight_col2 = st.columns(2)
        
        with insight_col1:
            if prediction < 2:
                st.success("✅ **LOW RISK** - Good production parameters")
                st.write("• Quality levels are expected to be high")
                st.write("• Current settings are optimal")
            elif prediction < 5:
                st.warning("⚠️ **MEDIUM RISK** - Monitor closely")
                st.write("• Some quality issues may occur")
                st.write("• Consider reviewing process parameters")
            else:
                st.error("🚨 **HIGH RISK** - Immediate attention required")
                st.write("• Significant quality issues expected")
                st.write("• Review and adjust production parameters")
        
        with insight_col2:
            st.write("**Recommendations:**")
            
            if downtime > 10:
                st.write("• Reduce machine downtime to improve quality")
            if quantity > 700:
                st.write("• Large batch size may impact quality control")
            if machine == 'M3':
                st.write("• M3 machine may require additional monitoring")
            
            st.write("• Regular quality checks recommended")
            st.write("• Document any process variations")
