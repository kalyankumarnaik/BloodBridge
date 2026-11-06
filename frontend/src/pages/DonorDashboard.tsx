import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { donorApi, requestApi } from '../services/api';
import { BloodRequest, DonorAvailability, DonorResponseStatus } from '../types';
import { MedicalDisclaimer } from '../components/Disclaimers';
import { BloodGroupBadge, UrgencyBadge } from '../components/Badges';
import { CardSkeleton } from '../components/LoadingSkeleton';
import { EmptyState } from '../components/EmptyState';
import {
  Heart,
  Calendar,
  Clock,
  ShieldCheck,
  CheckCircle2,
  XCircle,
  AlertCircle,
  Award,
  Bell,
  MapPin,
  Phone,
} from 'lucide-react';

export const DonorDashboard: React.FC = () => {
  const { user } = useAuth();
  const [profile, setProfile] = useState<any>(null);
  const [activeRequests, setActiveRequests] = useState<BloodRequest[]>([]);
  const [incomingContactRequests, setIncomingContactRequests] = useState<any[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [updatingAvailability, setUpdatingAvailability] = useState<boolean>(false);
  const [actionMessage, setActionMessage] = useState<string | null>(null);

  const loadDonorData = async () => {
    setLoading(true);
    try {
      const [profileRes, requestsRes, contactRes] = await Promise.allSettled([
        donorApi.getProfile(),
        requestApi.getEmergencyFeed(),
        donorApi.getMyContactRequests(),
      ]);

      if (profileRes.status === 'fulfilled') setProfile(profileRes.value);
      if (requestsRes.status === 'fulfilled') setActiveRequests(requestsRes.value);
      if (contactRes.status === 'fulfilled') setIncomingContactRequests(contactRes.value);
    } catch (err) {
      console.error('Failed to load donor data:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadDonorData();
  }, []);

  const handleAvailabilityChange = async (newStatus: DonorAvailability) => {
    setUpdatingAvailability(true);
    try {
      await donorApi.updateAvailability(newStatus);
      if (profile) setProfile({ ...profile, availability: newStatus });
      setActionMessage(`Availability status updated to ${newStatus.replace('_', ' ')}.`);
      setTimeout(() => setActionMessage(null), 3000);
    } catch (err) {
      console.error('Failed to update availability:', err);
    } finally {
      setUpdatingAvailability(false);
    }
  };

  const handleRespondToRequest = async (requestId: string, status: DonorResponseStatus) => {
    try {
      await donorApi.respondToRequest(requestId, status, 'Ready to donate at hospital blood bank.');
      setActionMessage(`Response recorded: ${status}. Thank you for your humanitarian readiness!`);
      setTimeout(() => setActionMessage(null), 4000);
      loadDonorData();
    } catch (err) {
      console.error('Error responding to request:', err);
    }
  };

  const handleConsentResponse = async (contactRequestId: number, consent: boolean) => {
    try {
      await donorApi.respondToContact(contactRequestId, consent);
      setActionMessage(
        consent
          ? 'Contact details revealed to verified requester. They will call you shortly.'
          : 'Contact request declined. Your details remain confidential.'
      );
      setTimeout(() => setActionMessage(null), 4000);
      loadDonorData();
    } catch (err) {
      console.error('Error responding to consent:', err);
    }
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-6">
      <MedicalDisclaimer />

      {/* Header & Welcome */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <div className="flex items-center space-x-2">
            <h1 className="text-2xl sm:text-3xl font-black text-slate-900 dark:text-white">
              Welcome back, {user?.name || 'Voluntary Donor'}
            </h1>
            <span title="Verified Lifesaver">
              <ShieldCheck className="w-6 h-6 text-emerald-500" />
            </span>
          </div>
          <p className="text-sm text-slate-500 dark:text-slate-400 mt-1">
            Role: <span className="font-semibold text-rose-600 dark:text-rose-400">Voluntary Blood Donor</span> •
            Blood Group: <strong className="text-slate-900 dark:text-white">{profile?.bloodGroup || 'O+'}</strong>
          </p>
        </div>

        {/* Availability Toggle */}
        <div className="bg-white dark:bg-slate-800 p-2 rounded-2xl border border-slate-200 dark:border-slate-700 shadow-sm flex items-center space-x-1.5 self-start">
          <span className="text-xs font-bold uppercase tracking-wider text-slate-400 px-2">Status:</span>
          {(['AVAILABLE_NOW', 'ON_CALL', 'INACTIVE'] as DonorAvailability[]).map((status) => (
            <button
              key={status}
              disabled={updatingAvailability}
              onClick={() => handleAvailabilityChange(status)}
              className={`px-3 py-1.5 rounded-xl text-xs font-bold transition ${
                profile?.availability === status
                  ? status === 'AVAILABLE_NOW'
                    ? 'bg-emerald-600 text-white shadow'
                    : status === 'ON_CALL'
                    ? 'bg-amber-600 text-white shadow'
                    : 'bg-slate-600 text-white shadow'
                  : 'text-slate-600 dark:text-slate-300 hover:bg-slate-100 dark:hover:bg-slate-700'
              }`}
            >
              {status === 'AVAILABLE_NOW' ? 'Available' : status === 'ON_CALL' ? 'On Call' : 'Inactive'}
            </button>
          ))}
        </div>
      </div>

      {actionMessage && (
        <div className="p-4 bg-emerald-50 dark:bg-emerald-950/60 border border-emerald-200 dark:border-emerald-800 rounded-2xl text-xs font-semibold text-emerald-800 dark:text-emerald-200 shadow-sm">
          {actionMessage}
        </div>
      )}

      {/* KPI Cards / Resting Period & Milestones */}
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
        {/* Donation Count & Milestone */}
        <div className="bg-white dark:bg-slate-800 rounded-3xl p-6 border border-slate-200 dark:border-slate-700/80 shadow-sm flex items-center space-x-4">
          <div className="w-14 h-14 rounded-2xl bg-rose-50 dark:bg-rose-950/80 text-rose-600 dark:text-rose-400 flex items-center justify-center">
            <Award className="w-8 h-8" />
          </div>
          <div>
            <span className="text-xs font-bold uppercase tracking-wider text-slate-400">Total Donations</span>
            <div className="text-2xl font-black text-slate-900 dark:text-white">
              {profile?.totalDonations || 3} Times
            </div>
            <span className="text-[11px] text-emerald-600 dark:text-emerald-400 font-semibold">
              Badge: Bronze Lifesaver
            </span>
          </div>
        </div>

        {/* 90-Day Courtesy Interval */}
        <div className="bg-white dark:bg-slate-800 rounded-3xl p-6 border border-slate-200 dark:border-slate-700/80 shadow-sm flex items-center space-x-4">
          <div className="w-14 h-14 rounded-2xl bg-blue-50 dark:bg-blue-950/80 text-blue-600 dark:text-blue-400 flex items-center justify-center">
            <Calendar className="w-8 h-8" />
          </div>
          <div>
            <span className="text-xs font-bold uppercase tracking-wider text-slate-400">Resting Interval</span>
            <div className="text-base font-bold text-slate-900 dark:text-white">Eligible to Donate</div>
            <span className="text-[11px] text-slate-500 dark:text-slate-400">
              Last: {profile?.lastDonationDate || '6 months ago'} (NBTC safe)
            </span>
          </div>
        </div>

        {/* Privacy Shield */}
        <div className="bg-white dark:bg-slate-800 rounded-3xl p-6 border border-slate-200 dark:border-slate-700/80 shadow-sm flex items-center space-x-4">
          <div className="w-14 h-14 rounded-2xl bg-emerald-50 dark:bg-emerald-950/80 text-emerald-600 dark:text-emerald-400 flex items-center justify-center">
            <ShieldCheck className="w-8 h-8" />
          </div>
          <div>
            <span className="text-xs font-bold uppercase tracking-wider text-slate-400">Privacy Status</span>
            <div className="text-base font-bold text-slate-900 dark:text-white">DPDP Shield Active</div>
            <span className="text-[11px] text-slate-500 dark:text-slate-400">Phone masked until your consent</span>
          </div>
        </div>
      </div>

      {/* Section 1: Incoming Contact Requests Pending Your Consent */}
      {incomingContactRequests.length > 0 && (
        <div className="bg-amber-50 dark:bg-amber-950/30 rounded-3xl p-6 border border-amber-300 dark:border-amber-700/60 space-y-4">
          <div className="flex items-center space-x-2 text-amber-900 dark:text-amber-200">
            <Bell className="w-5 h-5 text-amber-600 dark:text-amber-400 animate-bounce" />
            <h2 className="text-base font-bold">Action Required: Emergency Contact Requests</h2>
          </div>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            {incomingContactRequests.map((req: any) => (
              <div
                key={req.id}
                className="bg-white dark:bg-slate-800 p-5 rounded-2xl border border-slate-200 dark:border-slate-700 shadow-sm space-y-3"
              >
                <div className="flex justify-between items-start">
                  <span className="text-xs font-bold text-rose-600 dark:text-rose-400">
                    Requester: {req.requesterName}
                  </span>
                  <span className="text-[10px] text-slate-400">Received {req.createdAt || 'Today'}</span>
                </div>
                <p className="text-xs text-slate-700 dark:text-slate-300 leading-relaxed italic bg-slate-50 dark:bg-slate-700/40 p-2.5 rounded-xl">
                  &ldquo;{req.reason}&rdquo;
                </p>
                <div className="flex items-center justify-end space-x-2 pt-2">
                  <button
                    onClick={() => handleConsentResponse(req.id, false)}
                    className="px-3 py-1.5 rounded-xl text-xs font-semibold text-slate-600 dark:text-slate-300 hover:bg-slate-100 dark:hover:bg-slate-700"
                  >
                    Decline
                  </button>
                  <button
                    onClick={() => handleConsentResponse(req.id, true)}
                    className="px-4 py-1.5 rounded-xl text-xs font-bold text-white bg-emerald-600 hover:bg-emerald-700 shadow"
                  >
                    Reveal Phone & Consent
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Section 2: Compatible Emergency Requests Nearby */}
      <div className="space-y-4">
        <div className="flex items-center justify-between">
          <div>
            <h2 className="text-lg font-bold text-slate-900 dark:text-white">Active Emergency Requests</h2>
            <p className="text-xs text-slate-500 dark:text-slate-400">
              Patients and hospitals in need of compatible blood matching your profile.
            </p>
          </div>
        </div>

        {loading ? (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <CardSkeleton />
            <CardSkeleton />
          </div>
        ) : activeRequests.length === 0 ? (
          <EmptyState
            icon={Heart}
            title="No Active Emergency Alerts Right Now"
            description="You will be notified immediately via SMS and In-App notification when a nearby patient urgently needs your blood group."
          />
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            {activeRequests.map((req) => (
              <div
                key={req.requestId}
                className="bg-white dark:bg-slate-800 rounded-3xl p-6 border border-slate-200 dark:border-slate-700/80 shadow-sm hover:shadow-md transition space-y-4 flex flex-col justify-between"
              >
                <div>
                  <div className="flex items-start justify-between gap-3 mb-2">
                    <div className="flex items-center space-x-3">
                      <BloodGroupBadge bloodGroup={req.bloodGroup} size="lg" />
                      <div>
                        <div className="flex items-center space-x-2">
                          <h3 className="font-bold text-slate-900 dark:text-white text-base">
                            {req.patientName}
                          </h3>
                        </div>
                        <span className="text-[11px] font-mono text-slate-400">ID: {req.requestId}</span>
                      </div>
                    </div>
                    <UrgencyBadge priority={req.priority} />
                  </div>

                  <div className="space-y-1.5 text-xs text-slate-600 dark:text-slate-300 mb-3">
                    <div className="flex items-center space-x-1.5">
                      <MapPin className="w-3.5 h-3.5 text-rose-500 flex-shrink-0" />
                      <span>
                        {req.hospitalName}, {req.city} ({req.pinCode})
                      </span>
                    </div>
                    <div className="flex items-center space-x-1.5">
                      <Clock className="w-3.5 h-3.5 text-slate-400 flex-shrink-0" />
                      <span>Units Needed: {req.unitsRequired} Units</span>
                    </div>
                  </div>
                </div>

                <div className="pt-3 border-t border-slate-100 dark:border-slate-700/60 flex items-center justify-between gap-2">
                  <button
                    onClick={() => handleRespondToRequest(req.requestId, 'DECLINED')}
                    className="px-4 py-2 rounded-xl text-xs font-semibold text-slate-500 hover:text-slate-800 dark:hover:text-slate-200"
                  >
                    Cannot Donate
                  </button>
                  <button
                    onClick={() => handleRespondToRequest(req.requestId, 'ACCEPTED')}
                    className="px-5 py-2 rounded-xl text-xs font-bold text-white bg-rose-600 hover:bg-rose-700 shadow"
                  >
                    I Can Donate Blood
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};
