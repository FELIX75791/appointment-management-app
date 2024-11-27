# Doctor's Appointment Management App

## Overview

The **Doctor Appointment Management App** is designed to help doctors efficiently manage appointments with their patients. The app provides robust features for scheduling, managing availability, retrieving patient and provider data, and managing recurring events. It streamlines administrative tasks, allowing doctors to focus on patient care.

---

## Features

### Appointment Management
- **Create Appointments**: Doctors or administrators can create appointments for patients with detailed input such as date, time, and comments.
- **Update Appointments**: Modify appointment details such as time, status, or service type.
- **Cancel Appointments**: Remove appointments as needed with ease.
- **View Appointment Details**: Access specific appointment details by appointment ID.

### Scheduling and Availability
- **Create Blocks**: Define time blocks when the doctor is unavailable.
- **Create Recurring Blocks**: Set recurring blocks for a specific range of dates or for an entire year.
- **Get Available Slots**: View the available time slots for a specific doctor on a given date.

### Appointment History and Insights
- **Get Appointment History**: Retrieve a complete appointment history for a specific doctor-patient combination.
- **View Provider's Daily Appointments**: Get a list of all appointments for a specific provider on a given date.
- **List All Provider Appointments**: Access all appointments for a specific provider.

---

## Benefits

### Efficiency for Doctors
- Eliminate double bookings and ensure optimal scheduling through the availability and blocking features.
- Reduce manual follow-ups with automated status updates and reminders.

### Convenience for Patients
- Easier access to doctor availability and appointment slots.
- Improved communication and faster updates on changes or cancellations.

### Administrative Improvements
- Comprehensive history and insights for better decision-making.
- Automation of repetitive tasks like recurring blocks or frequent appointment updates.

---

## How the Client App Uses the Appointment Management Service

Our **client application** is a specialized tool designed to leverage the general-purpose **Appointment Management Service** to provide doctors and patients with an intuitive interface for managing appointments.

### What the Client App Does
- **For Doctors**:
   - Allows scheduling appointments by directly interacting with the backend service.
   - Provides tools to block unavailable times and set recurring blocks efficiently.
   - Displays available slots in real-time by querying the service for current availability.
   - Retrieves patient appointment history and daily schedules for better patient management.
- **For Patients**:
   - Enables booking appointments by utilizing available slots retrieved from the backend service.
   - Sends and receives notifications for appointment confirmations, rescheduling, or cancellations.
   - Provides a seamless interface to communicate with doctors about their schedules or appointment changes.

### How It Uses the Service
The client app communicates with the Appointment Management Service using the following key interactions:
1. **Appointment Management**:
   - `POST /appointments/createAppointment`: Sends user input to create a new appointment.
   - `PUT /appointments/update`: Updates appointment details when changes are made.
   - `PUT /appointments/cancel/{id}`: Cancels an appointment by ID.

2. **Availability and Scheduling**:
   - `POST /appointments/createBlock`: Creates time blocks to mark unavailable periods.
   - `POST /appointments/createRecurringBlock`: Defines recurring blocks over a date range.
   - `GET /appointments/provider/{providerId}/available/date/{date}`: Fetches available slots for a specific provider on a given date.

3. **Insights and History**:
   - `GET /appointments/provider/{providerId}/date/{date}`: Retrieves the schedule for a provider on a specific day.
   - `GET /appointments/history`: Fetches the appointment history between a doctor and a patient.

By interacting with these endpoints, the client app transforms the service's general-purpose functionalities into a tailored experience for doctors and patients, providing a fast, reliable, and intuitive solution for appointment management.