from django.urls import path
from . import views

urlpatterns = [
    path('', views.ServiceListCreateView.as_view(), name='service-list'),
    path('<int:pk>/', views.ServiceDetailView.as_view(), name='service-detail'),
    path('request/', views.ServiceRequestCreateView.as_view(), name='service-request-create'),
    path('most-requested/', views.MostRequestedServiceListView.as_view(), name='most-request'),
    path('<category>/', views.ServiceListView.as_view(), name='service-list'),
    path('company/<int:company_id>/services', views.ServicesByCompanyView.as_view(), name='service-requests-by-company'),
]