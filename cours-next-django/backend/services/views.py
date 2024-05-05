from rest_framework import generics
from .models import Service, ServiceRequest, Company
from .serializers import ServiceSerializer, ServiceRequestSerializer
from django.db.models import Count
from rest_framework.views import APIView
from .models import Service
from django.shortcuts import get_list_or_404
from rest_framework.response import Response
from users.views import Company

class ServicesByCompanyView(APIView):
    """
    Retrieve all services offered by a specific company.
    """

    def get(self, request, company_id):
        try:
            company = Company.objects.get(id=company_id)
            services = Service.objects.filter(company=company)  # Corrected to filter services by company
            
            serializer = ServiceSerializer(services, many=True)
            
            return Response(serializer.data)
        except Company.DoesNotExist:
            return Response({'error': 'Company not found'}, status=404)  # Added appropriate HTTP status code



class ServiceListCreateView(generics.ListCreateAPIView):
    queryset = Service.objects.all()
    serializer_class = ServiceSerializer

class ServiceListView(APIView):
    def get(self, request, category):
        services = get_list_or_404(Service, field=category)
        serializer = ServiceSerializer(services, many=True)
        return Response(serializer.data)

class ServiceDetailView(generics.RetrieveUpdateDestroyAPIView):
    queryset = Service.objects.all()
    serializer_class = ServiceSerializer

class ServiceRequestCreateView(generics.CreateAPIView):
    queryset = ServiceRequest.objects.all()
    serializer_class = ServiceRequestSerializer

class MostRequestedServiceListView(generics.ListAPIView):
    serializer_class = ServiceSerializer

    def get_queryset(self):
        """
        Cette vue retourne les services triés par leur popularité (nombre de demandes),
        calculée en comptant le nombre d'instances de ServiceRequest associées à chaque Service.
        """
        return Service.objects.annotate(request_count=Count('servicerequest')).order_by('-request_count')