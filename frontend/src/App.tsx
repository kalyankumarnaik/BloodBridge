import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import { ThemeProvider } from './context/ThemeContext';
import { Navbar } from './components/Navbar';
import { Footer } from './components/Footer';
import { ProtectedRoute } from './components/ProtectedRoute';

// Pages
import { Home } from './pages/Home';
import { FindDonors } from './pages/FindDonors';
import { EmergencyRequest } from './pages/EmergencyRequest';
import { BloodCompatibilityGuide } from './pages/BloodCompatibilityGuide';
import { BloodBanks } from './pages/BloodBanks';
import { DonorRegistration } from './pages/DonorRegistration';
import { HospitalRegistration } from './pages/HospitalRegistration';
import { Login } from './pages/Login';
import { ForgotPassword } from './pages/ForgotPassword';
import { DonorDashboard } from './pages/DonorDashboard';
import { PatientDashboard } from './pages/PatientDashboard';
import { HospitalDashboard } from './pages/HospitalDashboard';
import { AdminDashboard } from './pages/AdminDashboard';
import { UserProfile } from './pages/UserProfile';
import { Notifications } from './pages/Notifications';
import { DonationHistory } from './pages/DonationHistory';
import { About } from './pages/About';
import { HowItWorks } from './pages/HowItWorks';
import { Contact } from './pages/Contact';
import { PrivacyPolicy } from './pages/PrivacyPolicy';
import { TermsAndConditions } from './pages/TermsAndConditions';

export const App: React.FC = () => {
  return (
    <ThemeProvider>
      <AuthProvider>
        <Router>
          <div className="min-h-screen flex flex-col bg-slate-50 dark:bg-slate-900 text-slate-900 dark:text-slate-100 transition-colors">
            <Navbar />
            <main className="flex-1">
              <Routes>
                {/* Public Discovery & Information */}
                <Route path="/" element={<Home />} />
                <Route path="/find-donors" element={<FindDonors />} />
                <Route path="/emergency-request" element={<EmergencyRequest />} />
                <Route path="/compatibility-guide" element={<BloodCompatibilityGuide />} />
                <Route path="/blood-banks" element={<BloodBanks />} />
                <Route path="/about" element={<About />} />
                <Route path="/how-it-works" element={<HowItWorks />} />
                <Route path="/contact" element={<Contact />} />
                <Route path="/privacy-policy" element={<PrivacyPolicy />} />
                <Route path="/terms" element={<TermsAndConditions />} />

                {/* Authentication */}
                <Route path="/register-donor" element={<DonorRegistration />} />
                <Route path="/hospital-register" element={<HospitalRegistration />} />
                <Route path="/login" element={<Login />} />
                <Route path="/forgot-password" element={<ForgotPassword />} />

                {/* Role-Specific Protected Dashboards */}
                <Route
                  path="/donor-dashboard"
                  element={
                    <ProtectedRoute allowedRoles={['DONOR', 'ROLE_DONOR']}>
                      <DonorDashboard />
                    </ProtectedRoute>
                  }
                />
                <Route
                  path="/patient-dashboard"
                  element={
                    <ProtectedRoute allowedRoles={['PATIENT', 'ROLE_PATIENT']}>
                      <PatientDashboard />
                    </ProtectedRoute>
                  }
                />
                <Route
                  path="/hospital"
                  element={
                    <ProtectedRoute allowedRoles={['HOSPITAL', 'ROLE_HOSPITAL']}>
                      <HospitalDashboard />
                    </ProtectedRoute>
                  }
                />
                <Route
                  path="/admin"
                  element={
                    <ProtectedRoute allowedRoles={['ADMIN', 'ROLE_ADMIN']}>
                      <AdminDashboard />
                    </ProtectedRoute>
                  }
                />

                {/* Authenticated User Actions */}
                <Route
                  path="/profile"
                  element={
                    <ProtectedRoute>
                      <UserProfile />
                    </ProtectedRoute>
                  }
                />
                <Route
                  path="/notifications"
                  element={
                    <ProtectedRoute>
                      <Notifications />
                    </ProtectedRoute>
                  }
                />
                <Route
                  path="/history"
                  element={
                    <ProtectedRoute allowedRoles={['DONOR', 'ROLE_DONOR']}>
                      <DonationHistory />
                    </ProtectedRoute>
                  }
                />

                {/* Fallback */}
                <Route path="*" element={<Navigate to="/" replace />} />
              </Routes>
            </main>
            <Footer />
          </div>
        </Router>
      </AuthProvider>
    </ThemeProvider>
  );
};

export default App;
