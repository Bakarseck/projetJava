from rest_framework import status, views
from rest_framework.response import Response
from .serializers import CustomerSerializer, CompanySerializer
from django.contrib.auth.hashers import make_password
from django.http import JsonResponse
from .models import User
from django.db.models import Q
from rest_framework.views import APIView
from .models import Customer, Company
from services.models import ServiceRequest
from services.serializers import ServiceRequestSerializer
from django.shortcuts import get_object_or_404

class CustomerProfileView(APIView):
    def get(self, request, pk):
        try:
            customer = Customer.objects.get(pk=pk)
            serializer = CustomerSerializer(customer)
            return Response(serializer.data)
        except Customer.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)

class CompanyProfileView(APIView):
    def get(self, request, pk):
        try:
            company = Company.objects.get(pk=pk)
            serializer = CompanySerializer(company)
            return Response(serializer.data)
        except Company.DoesNotExist:
            return Response(status=status.HTTP_404_NOT_FOUND)
        
class CustomerRegisterView(views.APIView):
    def post(self, request):
        serializer = CustomerSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class CompanyRegisterView(views.APIView):
    def post(self, request):
        serializer = CompanySerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

from django.http import JsonResponse
from django.db.models import Q
from rest_framework import views
from .models import User, Customer, Company
from .serializers import UserSerializer, CustomerSerializer, CompanySerializer

class LoginView(views.APIView):
    def post(self, request):
        login = request.data.get('email')
        password = request.data.get('password')

        try:
            user = User.objects.get(Q(email=login) | Q(username=login))
        except User.DoesNotExist:
            return JsonResponse({'error': "Login incorrect"}, status=401)

        if not user.check_password(password):
            return JsonResponse({'error': 'Mot de passe incorrect'}, status=401)

        if hasattr(user, 'customer'):
            user_type = 'customer'
            user_id = user.customer.id
            extra_data = CustomerSerializer(user.customer).data
        elif hasattr(user, 'company'):
            user_type = 'company'
            user_id = user.company.id
            extra_data = CompanySerializer(user.company).data
        else:
            user_type = 'basic'
            user_id = user.id
            extra_data = {}

        response_data = {
            'user': {
                'id': user_id,
                'id_user': user.id,
                'email': user.email,
                'username': user.username,
                'type': user_type,
            },
            'profile': extra_data,
            'message': 'Utilisateur connecté avec succès'
        }

        return JsonResponse(response_data, status=200)


class UserRequestedServicesView(APIView):
    """
    Retrieve service requests made by a specific user.
    """

    def get(self, request, user_id):
        user = get_object_or_404(User, pk=user_id)
        service_requests = ServiceRequest.objects.filter(customer=user)
        serializer = ServiceRequestSerializer(service_requests, many=True)
        return Response(serializer.data)

class CompanyRequestedServicesView(APIView):
    """
    Retrieve service requests for services provided by a specific company.
    """

    def get(self, request, company_id):
        company = get_object_or_404(Company, pk=company_id)
        service_requests = ServiceRequest.objects.filter(service__company=company)
        serializer = ServiceRequestSerializer(service_requests, many=True)
        return Response(serializer.data)    