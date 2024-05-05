from django.urls import path
from . import views

urlpatterns = [
    path('register/customer/', views.CustomerRegisterView.as_view(), name='customer-register'),
    path('register/company/', views.CompanyRegisterView.as_view(), name='company-register'),
    path('login/', views.LoginView.as_view(), name='login'),
    path('customer/<int:pk>/', views.CustomerProfileView.as_view(), name='customer-profile'),
    path('company/<int:pk>/', views.CompanyProfileView.as_view(), name='company-profile'),
    path('customer/<int:user_id>/services-requested/', views.UserRequestedServicesView.as_view(), name='user-requested-services'),
    path('company/<int:company_id>/requested-services/', views.CompanyRequestedServicesView.as_view(), name='company-requested-services'),
]
