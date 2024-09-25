from django.contrib import admin
from django.urls import path
from feedback_app.views import feedback_view

urlpatterns = [
    path('admin/', admin.site.urls),
    path('', feedback_view, name='feedback_app'),
]