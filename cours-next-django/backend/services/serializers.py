from rest_framework import serializers
from .models import Service, ServiceRequest
from users.models import Company, User, Customer

class CustomerSerializer(serializers.ModelSerializer):
    class Meta:
        model = Customer
        fields = '__all__'

class CompanySerializer(serializers.ModelSerializer):
    class Meta:
        model = Company
        fields = '__all__'

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('email', 'username')

class CompanySerializer(serializers.ModelSerializer):
    user = UserSerializer(read_only=True)
    
    class Meta:
        model = Company
        fields = ['user', 'field_of_work']
            
class ServiceSerializer(serializers.ModelSerializer):
    # company = CompanySerializer(read_only=True)
    
    class Meta:
        model = Service
        fields = ['id', 'name', 'description', 'field', 'price_per_hour', 'date_created', 'company']

class ServiceRequestSerializer(serializers.ModelSerializer):
    cost = serializers.SerializerMethodField()

    class Meta:
        model = ServiceRequest
        fields = ['id', 'service', 'customer', 'address', 'service_time', 'date_requested', 'cost']

    def get_cost(self, obj):
        return obj.calculate_cost()
