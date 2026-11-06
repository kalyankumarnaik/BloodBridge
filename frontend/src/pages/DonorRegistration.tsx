import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { BloodGroup, DonorAvailability } from '../types';
import { MedicalDisclaimer, PrivacyDisclaimer } from '../components/Disclaimers';
import { Heart, Shield, CheckCircle2, User, Mail, Phone, Lock, Calendar, MapPin } from 'lucide-react';

export const DonorRegistration: React.FC = () => {
  const navigate = useNavigate();
  const { registerDonor } = useAuth();

  const [fullName, setFullName] = useState('');
  const [email, setEmail] = useState('');
  const [phone, setPhone] = useState('');
  const [password, setPassword] = useState('');
  const [bloodGroup, setBloodGroup] = useState<BloodGroup>('O_POSITIVE');
  const [dateOfBirth, setDateOfBirth] = useState('2000-01-01');
  const [city, setCity] = useState('Bangalore');
  const [state, setState] = useState('Karnataka');
  const [pinCode, setPinCode] = useState('');
  const [lastDonationDate, setLastDonationDate] = useState('');
  const [availability, setAvailability] = useState<DonorAvailability>('AVAILABLE_NOW');
  const [emergencySmsConsent, setEmergencySmsConsent] = useState(true);
  const [dpdpConsent, setDpdpConsent] = useState(false);

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const indianStates = [
    'Andhra Pradesh', 'Arunachal Pradesh', 'Assam', 'Bihar', 'Chhattisgarh', 'Goa', 'Gujarat',
    'Haryana', 'Himachal Pradesh', 'Jharkhand', 'Karnataka', 'Kerala', 'Madhya Pradesh',
    'Maharashtra', 'Manipur', 'Meghalaya', 'Mizoram', 'Nagaland', 'Odisha', 'Punjab',
    'Rajasthan', 'Sikkim', 'Tamil Nadu', 'Telangana', 'Tripura', 'Uttar Pradesh', 'Uttarakhand',
    'West Bengal', 'Delhi', 'Jammu and Kashmir', 'Ladakh', 'Puducherry'
  ];

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!dpdpConsent) {
      setError('Please accept the DPDP Act 2023 consent policy and blood donation ethics.');
      return;
    }
    if (!/^\d{10}$/.test(phone.trim().replace('+91', ''))) {
      setError('Please enter a valid 10-digit Indian mobile number.');
      return;
    }
    if (!/^\d{6}$/.test(pinCode.trim())) {
      setError('Please enter a valid 6-digit Indian PIN Code.');
      return;
    }

    // Age validation: 18 to 65 years
    const birthYear = new Date(dateOfBirth).getFullYear();
    const currentYear = new Date().getFullYear();
    const age = currentYear - birthYear;
    if (age < 18 || age > 65) {
      setError('Indian National Blood Transfusion Council requires voluntary donors to be between 18 and 65 years old.');
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const formattedPhone = phone.startsWith('+91') ? phone.trim() : `+91${phone.trim()}`;
      await registerDonor({
        fullName: fullName.trim(),
        email: email.trim(),
        phone: formattedPhone,
        password,
        bloodGroup,
        dateOfBirth,
        city: city.trim(),
        state: state.trim(),
        pinCode: pinCode.trim(),
        lastDonationDate: lastDonationDate || undefined,
        availability,
        emergencySmsConsent,
      });
      navigate('/donor-dashboard');
    } catch (err: any) {
      console.error('Registration failed:', err);
      setError(
        err.response?.data?.message ||
          'Registration failed. An account with this phone number or email may already exist.'
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-3xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-6">
      <MedicalDisclaimer />

      <form
        onSubmit={handleSubmit}
        className="bg-white dark:bg-slate-800 rounded-3xl p-6 sm:p-10 border border-slate-200 dark:border-slate-700/80 shadow-sm space-y-6"
      >
        <div className="text-center max-w-lg mx-auto space-y-2">
          <div className="w-12 h-12 bg-rose-100 dark:bg-rose-950/80 text-rose-600 dark:text-rose-400 rounded-2xl flex items-center justify-center mx-auto">
            <Heart className="w-6 h-6 fill-rose-600 dark:fill-rose-400" />
          </div>
          <h1 className="text-2xl sm:text-3xl font-black text-slate-900 dark:text-white">
            Register as a Voluntary Donor
          </h1>
          <p className="text-xs sm:text-sm text-slate-500 dark:text-slate-400">
            Join thousands of voluntary lifesavers across India. We strictly guard your privacy and will never broadcast
            your telephone without your consent.
          </p>
        </div>

        {error && (
          <div className="p-4 bg-rose-50 dark:bg-rose-950/60 border border-rose-200 dark:border-rose-800 rounded-xl text-xs text-rose-700 dark:text-rose-300">
            {error}
          </div>
        )}

        {/* Section 1: Account & Credentials */}
        <div className="space-y-4">
          <h3 className="text-xs font-bold uppercase tracking-wider text-slate-400 border-b border-slate-100 dark:border-slate-700/60 pb-2">
            1. Account & Login
          </h3>
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div>
              <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                Full Name <span className="text-rose-500">*</span>
              </label>
              <div className="relative">
                <User className="w-4 h-4 text-slate-400 absolute left-3 top-3.5" />
                <input
                  type="text"
                  required
                  value={fullName}
                  onChange={(e) => setFullName(e.target.value)}
                  placeholder="Rahul Sharma"
                  className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white pl-10 pr-3 py-2.5 focus:ring-2 focus:ring-rose-500 focus:outline-none"
                />
              </div>
            </div>

            <div>
              <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                Email Address <span className="text-rose-500">*</span>
              </label>
              <div className="relative">
                <Mail className="w-4 h-4 text-slate-400 absolute left-3 top-3.5" />
                <input
                  type="email"
                  required
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  placeholder="rahul@example.com"
                  className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white pl-10 pr-3 py-2.5 focus:ring-2 focus:ring-rose-500 focus:outline-none"
                />
              </div>
            </div>

            <div>
              <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                Mobile Number (India) <span className="text-rose-500">*</span>
              </label>
              <div className="relative">
                <Phone className="w-4 h-4 text-slate-400 absolute left-3 top-3.5" />
                <input
                  type="tel"
                  required
                  value={phone}
                  onChange={(e) => setPhone(e.target.value)}
                  placeholder="9876543210"
                  className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white pl-10 pr-3 py-2.5 focus:ring-2 focus:ring-rose-500 focus:outline-none font-mono"
                />
              </div>
            </div>

            <div>
              <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                Create Password <span className="text-rose-500">*</span>
              </label>
              <div className="relative">
                <Lock className="w-4 h-4 text-slate-400 absolute left-3 top-3.5" />
                <input
                  type="password"
                  required
                  minLength={8}
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  placeholder="Min 8 characters"
                  className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white pl-10 pr-3 py-2.5 focus:ring-2 focus:ring-rose-500 focus:outline-none"
                />
              </div>
            </div>
          </div>
        </div>

        {/* Section 2: Blood & Eligibility */}
        <div className="space-y-4 pt-2">
          <h3 className="text-xs font-bold uppercase tracking-wider text-slate-400 border-b border-slate-100 dark:border-slate-700/60 pb-2">
            2. Blood Group & Eligibility
          </h3>
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
            <div>
              <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                Blood Group <span className="text-rose-500">*</span>
              </label>
              <select
                value={bloodGroup}
                onChange={(e) => setBloodGroup(e.target.value as BloodGroup)}
                className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-2.5 focus:ring-2 focus:ring-rose-500 focus:outline-none"
              >
                <option value="O_NEGATIVE">O Negative (O-)</option>
                <option value="O_POSITIVE">O Positive (O+)</option>
                <option value="A_NEGATIVE">A Negative (A-)</option>
                <option value="A_POSITIVE">A Positive (A+)</option>
                <option value="B_NEGATIVE">B Negative (B-)</option>
                <option value="B_POSITIVE">B Positive (B+)</option>
                <option value="AB_NEGATIVE">AB Negative (AB-)</option>
                <option value="AB_POSITIVE">AB Positive (AB+)</option>
              </select>
            </div>

            <div>
              <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                Date of Birth (18-65 yrs) <span className="text-rose-500">*</span>
              </label>
              <input
                type="date"
                required
                value={dateOfBirth}
                onChange={(e) => setDateOfBirth(e.target.value)}
                className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-2.5 focus:ring-2 focus:ring-rose-500 focus:outline-none"
              />
            </div>

            <div>
              <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                Last Donation Date
              </label>
              <input
                type="date"
                value={lastDonationDate}
                onChange={(e) => setLastDonationDate(e.target.value)}
                className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-2.5 focus:ring-2 focus:ring-rose-500 focus:outline-none"
              />
            </div>
          </div>

          <div>
            <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
              Initial Availability Status
            </label>
            <div className="grid grid-cols-1 sm:grid-cols-3 gap-2">
              <button
                type="button"
                onClick={() => setAvailability('AVAILABLE_NOW')}
                className={`p-3 rounded-xl text-xs font-bold border transition text-center ${
                  availability === 'AVAILABLE_NOW'
                    ? 'bg-emerald-600 text-white border-emerald-600'
                    : 'bg-slate-50 dark:bg-slate-700 text-slate-700 dark:text-slate-300 border-slate-200 dark:border-slate-600'
                }`}
              >
                Available Now
              </button>
              <button
                type="button"
                onClick={() => setAvailability('ON_CALL')}
                className={`p-3 rounded-xl text-xs font-bold border transition text-center ${
                  availability === 'ON_CALL'
                    ? 'bg-amber-600 text-white border-amber-600'
                    : 'bg-slate-50 dark:bg-slate-700 text-slate-700 dark:text-slate-300 border-slate-200 dark:border-slate-600'
                }`}
              >
                On Call (Urgent Only)
              </button>
              <button
                type="button"
                onClick={() => setAvailability('INACTIVE')}
                className={`p-3 rounded-xl text-xs font-bold border transition text-center ${
                  availability === 'INACTIVE'
                    ? 'bg-slate-600 text-white border-slate-600'
                    : 'bg-slate-50 dark:bg-slate-700 text-slate-700 dark:text-slate-300 border-slate-200 dark:border-slate-600'
                }`}
              >
                Resting / Inactive
              </button>
            </div>
          </div>
        </div>

        {/* Section 3: Location */}
        <div className="space-y-4 pt-2">
          <h3 className="text-xs font-bold uppercase tracking-wider text-slate-400 border-b border-slate-100 dark:border-slate-700/60 pb-2">
            3. City & PIN Code (For Distance Matching)
          </h3>
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
            <div>
              <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                City <span className="text-rose-500">*</span>
              </label>
              <input
                type="text"
                required
                value={city}
                onChange={(e) => setCity(e.target.value)}
                placeholder="Bangalore"
                className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-2.5 focus:ring-2 focus:ring-rose-500 focus:outline-none"
              />
            </div>

            <div>
              <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                State <span className="text-rose-500">*</span>
              </label>
              <select
                value={state}
                onChange={(e) => setState(e.target.value)}
                className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-2.5 focus:ring-2 focus:ring-rose-500 focus:outline-none"
              >
                {indianStates.map((st) => (
                  <option key={st} value={st}>
                    {st}
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                6-Digit PIN Code <span className="text-rose-500">*</span>
              </label>
              <input
                type="text"
                maxLength={6}
                required
                value={pinCode}
                onChange={(e) => setPinCode(e.target.value)}
                placeholder="560001"
                className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-2.5 focus:ring-2 focus:ring-rose-500 focus:outline-none font-mono"
              />
            </div>
          </div>
        </div>

        {/* Section 4: Privacy & DPDP Consent */}
        <div className="space-y-3 pt-2">
          <PrivacyDisclaimer />

          <label className="flex items-start space-x-2.5 text-xs text-slate-600 dark:text-slate-300 cursor-pointer">
            <input
              type="checkbox"
              checked={emergencySmsConsent}
              onChange={(e) => setEmergencySmsConsent(e.target.checked)}
              className="mt-0.5 rounded text-rose-600 focus:ring-rose-500"
            />
            <span>
              I consent to receive high-priority emergency notifications via SMS and In-App alerts when someone nearby
              urgently requires my blood type.
            </span>
          </label>

          <label className="flex items-start space-x-2.5 text-xs text-slate-600 dark:text-slate-300 cursor-pointer">
            <input
              type="checkbox"
              required
              checked={dpdpConsent}
              onChange={(e) => setDpdpConsent(e.target.checked)}
              className="mt-0.5 rounded text-rose-600 focus:ring-rose-500"
            />
            <span>
              I understand that under DPDP Act 2023, BloodBridge masks my phone number until I explicitly accept an
              emergency request. I confirm I am registering voluntarily without any financial consideration.
            </span>
          </label>
        </div>

        <div className="pt-4 border-t border-slate-100 dark:border-slate-700 flex flex-col sm:flex-row items-center justify-between gap-4">
          <p className="text-xs text-slate-500">
            Already registered?{' '}
            <Link to="/login" className="font-bold text-rose-600 dark:text-rose-400 hover:underline">
              Sign In
            </Link>
          </p>

          <button
            type="submit"
            disabled={loading}
            className="w-full sm:w-auto inline-flex items-center justify-center space-x-2 px-8 py-3.5 rounded-2xl bg-rose-600 hover:bg-rose-700 text-white font-bold text-sm shadow-md transition disabled:opacity-50"
          >
            {loading ? (
              <span>Creating Profile...</span>
            ) : (
              <>
                <Heart className="w-4 h-4 fill-white" />
                <span>Register as Voluntary Donor</span>
              </>
            )}
          </button>
        </div>
      </form>
    </div>
  );
};
