from django.db import models
from users.models import User, Company
from decimal import Decimal

class Service(models.Model):

    FIELD_CHOICES = [
        ('All in One', 'All in One'),
        ('Air Conditioner', 'Air Conditioner'),
        ('Carpentry', 'Carpentry'),
        ('Electricity', 'Electricity'),
        ('Gardening', 'Gardening'),
        ('Home Machines', 'Home Machines'),
        ('Housekeeping', 'Housekeeping'),
        ('Interior Design', 'Interior Design'),
        ('Locks', 'Locks'),
        ('Painting', 'Painting'),
        ('Plumbing', 'Plumbing'),
        ('Water Heaters', 'Water Heaters'),
    ]

    name = models.CharField(max_length=255)
    description = models.TextField()
    field = models.CharField(max_length=50, choices=FIELD_CHOICES)
    price_per_hour = models.DecimalField(max_digits=6, decimal_places=2)
    date_created = models.DateTimeField(auto_now_add=True)
    company = models.ForeignKey(Company, on_delete=models.CASCADE)
    
    class Meta:
        ordering = ['-date_created']

class ServiceRequest(models.Model):
    service = models.ForeignKey(Service, on_delete=models.CASCADE)
    customer = models.ForeignKey(User, on_delete=models.CASCADE, limit_choices_to={'is_customer': True})
    address = models.CharField(max_length=255)
    service_time = models.FloatField(help_text="Service time in hours")
    date_requested = models.DateTimeField(auto_now_add=True)
    
    def calculate_cost(self):
        return Decimal(self.service_time) * self.service.price_per_hour
