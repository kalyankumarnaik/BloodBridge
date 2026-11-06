import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { requestApi } from '../services/api';
import { BloodRequest, RequestStatus } from '../types';
import { MedicalDisclaimer } from '../components/Disclaimers';
import { BloodGroupBadge, UrgencyBadge, StatusBadge } from '../components/Badges';
import { CardSkeleton } from '../components/LoadingSkeleton';
import { EmptyState } from '../components/EmptyState';
import {
  AlertCircle,
  Plus,
  Share2,
  CheckCircle2,
  XCircle,
  Phone,
  Clock,
  MapPin,
  ChevronRight,
  ShieldCheck,
} from 'lucide-react';

export const PatientDashboard: React.FC = () => {
  const { user } = useAuth();
  const [requests, setRequests] = useState<BloodRequest[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [selectedRequest, setSelectedRequest] = useState<BloodRequest | null>(null);
  const [actionMessage, setActionMessage] = useState<string | null>(null);

  const loadRequests = async () => {
    setLoading(true);
    try {
      const data = await requestApi.getMyRequests();
      setRequests(data);
      if (data.length > 0) setSelectedRequest(data[0]);
    } catch (err) {
      console.error('Failed to load patient requests:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadRequests();
  }, []);

  const handleStatusChange = async (requestId: string, newStatus: RequestStatus) => {
    try {
      await requestApi.updateStatus(requestId, newStatus);
      setActionMessage(`Request status updated to ${newStatus}.`);
      setTimeout(() => setActionMessage(null), 3000);
      loadRequests();
    } catch (err) {
      console.error('Failed to update request status:', err);
    }
  };

  const getWhatsAppShareUrl = (req: BloodRequest) => {
    const text = `🚨 *URGENT BLOOD REQUIREMENT* 🚨\n\nPatient: ${req.patientName}\nBlood Group: *${req.bloodGroup}*\nUnits: ${req.unitsRequired}\nHospital: ${req.hospitalName}, ${req.city}\nStatus: *${req.status}*\n\nPlease respond or share via BloodBridge:\n${window.location.origin}/find-donors?requestId=${req.requestId}`;
    return `https://wa.me/?text=${encodeURIComponent(text)}`;
  };

  const statusSteps: RequestStatus[] = ['PENDING', 'VERIFIED', 'ACTIVE', 'DONOR_RESPONDED', 'FULFILLED'];

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-6">
      <MedicalDisclaimer />

      {/* Header */}
      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
        <div>
          <h1 className="text-2xl sm:text-3xl font-black text-slate-900 dark:text-white">
            Patient / Requester Dashboard
          </h1>
          <p className="text-sm text-slate-500 dark:text-slate-400 mt-1">
            Track your emergency blood requests, view responding voluntary donors, and manage hospital communications.
          </p>
        </div>

        <Link
          to="/emergency-request"
          className="inline-flex items-center space-x-2 px-5 py-2.5 rounded-2xl bg-rose-600 hover:bg-rose-700 text-white font-bold text-xs shadow-md transition self-start"
        >
          <Plus className="w-4 h-4" />
          <span>New Emergency Request</span>
        </Link>
      </div>

      {actionMessage && (
        <div className="p-4 bg-emerald-50 dark:bg-emerald-950/60 border border-emerald-200 dark:border-emerald-800 rounded-2xl text-xs font-semibold text-emerald-800 dark:text-emerald-200 shadow-sm">
          {actionMessage}
        </div>
      )}

      {loading ? (
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <CardSkeleton />
          <div className="md:col-span-2">
            <CardSkeleton />
          </div>
        </div>
      ) : requests.length === 0 ? (
        <EmptyState
          icon={AlertCircle}
          title="No Blood Requests Created Yet"
          description="If a family member or patient needs blood, create an emergency request to immediately alert compatible donors nearby."
          actionText="Create Blood Request"
          onAction={() => (window.location.href = '/emergency-request')}
        />
      ) : (
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          {/* Left Column: Requests List */}
          <div className="space-y-3">
            <h2 className="text-xs font-bold uppercase tracking-wider text-slate-400">Your Active Requests</h2>
            {requests.map((req) => (
              <div
                key={req.requestId}
                onClick={() => setSelectedRequest(req)}
                className={`p-5 rounded-2xl border cursor-pointer transition ${
                  selectedRequest?.requestId === req.requestId
                    ? 'bg-rose-50/70 dark:bg-rose-950/30 border-rose-300 dark:border-rose-800 shadow-sm'
                    : 'bg-white dark:bg-slate-800 border-slate-200 dark:border-slate-700/80 hover:bg-slate-50 dark:hover:bg-slate-700/40'
                }`}
              >
                <div className="flex items-start justify-between gap-2 mb-2">
                  <div className="flex items-center space-x-2">
                    <BloodGroupBadge bloodGroup={req.bloodGroup} size="sm" />
                    <span className="font-bold text-slate-900 dark:text-white text-sm">{req.patientName}</span>
                  </div>
                  <UrgencyBadge priority={req.priority} />
                </div>
                <p className="text-xs text-slate-500 dark:text-slate-400 line-clamp-1">{req.hospitalName}</p>
                <div className="flex items-center justify-between text-[11px] text-slate-400 mt-3 pt-2 border-t border-slate-100 dark:border-slate-700/60">
                  <span className="font-mono">{req.requestId}</span>
                  <StatusBadge status={req.status} />
                </div>
              </div>
            ))}
          </div>

          {/* Right Column: Selected Request Details & Stepper */}
          {selectedRequest && (
            <div className="lg:col-span-2 bg-white dark:bg-slate-800 rounded-3xl p-6 sm:p-8 border border-slate-200 dark:border-slate-700/80 shadow-sm space-y-6">
              {/* Header Info */}
              <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 pb-4 border-b border-slate-100 dark:border-slate-700">
                <div className="flex items-center space-x-3">
                  <BloodGroupBadge bloodGroup={selectedRequest.bloodGroup} size="lg" />
                  <div>
                    <h2 className="text-xl font-bold text-slate-900 dark:text-white">
                      {selectedRequest.patientName}
                    </h2>
                    <span className="text-xs text-slate-400 font-mono">ID: {selectedRequest.requestId}</span>
                  </div>
                </div>

                <div className="flex items-center space-x-2">
                  <a
                    href={getWhatsAppShareUrl(selectedRequest)}
                    target="_blank"
                    rel="noreferrer"
                    className="inline-flex items-center space-x-1.5 px-3 py-1.5 rounded-xl text-xs font-bold bg-emerald-600 hover:bg-emerald-700 text-white shadow"
                  >
                    <Share2 className="w-3.5 h-3.5" />
                    <span>WhatsApp</span>
                  </a>
                </div>
              </div>

              {/* Progress Stepper */}
              <div>
                <h3 className="text-xs font-bold uppercase tracking-wider text-slate-400 mb-3">
                  Lifecycle Progress
                </h3>
                <div className="grid grid-cols-5 gap-1.5 text-center">
                  {statusSteps.map((step, idx) => {
                    const currentIdx = statusSteps.indexOf(selectedRequest.status);
                    const isPassed = currentIdx >= idx;
                    const isCurrent = selectedRequest.status === step;

                    return (
                      <div key={step} className="space-y-1">
                        <div
                          className={`h-2 rounded-full transition ${
                            isPassed ? 'bg-rose-600 dark:bg-rose-500' : 'bg-slate-200 dark:bg-slate-700'
                          }`}
                        />
                        <span
                          className={`text-[10px] block truncate font-medium ${
                            isCurrent
                              ? 'font-bold text-rose-600 dark:text-rose-400'
                              : isPassed
                              ? 'text-slate-700 dark:text-slate-300'
                              : 'text-slate-400'
                          }`}
                        >
                          {step.replace('_', ' ')}
                        </span>
                      </div>
                    );
                  })}
                </div>
              </div>

              {/* Hospital & Case Details */}
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 text-xs bg-slate-50 dark:bg-slate-700/40 p-4 rounded-2xl border border-slate-100 dark:border-slate-700">
                <div>
                  <span className="text-slate-400 block font-medium">Hospital & Location:</span>
                  <span className="font-semibold text-slate-800 dark:text-slate-200">
                    {selectedRequest.hospitalName}, {selectedRequest.hospitalAddress}
                  </span>
                  <span className="block text-slate-500">
                    {selectedRequest.city}, {selectedRequest.state} ({selectedRequest.pinCode})
                  </span>
                </div>
                <div>
                  <span className="text-slate-400 block font-medium">Requirement & Urgency:</span>
                  <span className="font-semibold text-slate-800 dark:text-slate-200">
                    {selectedRequest.unitsRequired} Units • {selectedRequest.priority} Priority
                  </span>
                  <span className="block text-slate-500">Needed by: {selectedRequest.requiredBy || 'Immediate'}</span>
                </div>
              </div>

              {/* Responding Donors Section */}
              <div className="space-y-3">
                <h3 className="text-xs font-bold uppercase tracking-wider text-slate-400">
                  Responding Donors & Consents
                </h3>
                {selectedRequest.donorResponses && selectedRequest.donorResponses.length > 0 ? (
                  <div className="space-y-2">
                    {selectedRequest.donorResponses.map((res: any) => (
                      <div
                        key={res.id}
                        className="p-4 rounded-2xl bg-slate-50 dark:bg-slate-700/50 border border-slate-200 dark:border-slate-600 flex items-center justify-between"
                      >
                        <div>
                          <div className="flex items-center space-x-2">
                            <span className="text-sm font-bold text-slate-900 dark:text-white">
                              {res.donorDisplayName}
                            </span>
                            <span className="text-xs px-2 py-0.5 rounded-full bg-emerald-100 dark:bg-emerald-950 text-emerald-700 dark:text-emerald-300 font-semibold">
                              Consented
                            </span>
                          </div>
                          <span className="text-xs text-slate-500">{res.notes || 'Accepted request'}</span>
                        </div>

                        {res.donorPhone && (
                          <a
                            href={`tel:${res.donorPhone}`}
                            className="inline-flex items-center space-x-1.5 px-3 py-1.5 rounded-xl text-xs font-bold text-white bg-emerald-600 hover:bg-emerald-700 shadow"
                          >
                            <Phone className="w-3.5 h-3.5" />
                            <span>Call {res.donorPhone}</span>
                          </a>
                        )}
                      </div>
                    ))}
                  </div>
                ) : (
                  <p className="text-xs text-slate-500 italic bg-slate-50 dark:bg-slate-700/30 p-3 rounded-xl">
                    No donor has accepted yet. Matching notifications are broadcasting to nearby voluntary donors.
                  </p>
                )}
              </div>

              {/* Actions Footer */}
              <div className="pt-4 border-t border-slate-100 dark:border-slate-700 flex flex-wrap items-center justify-between gap-3">
                <div className="space-x-2">
                  {selectedRequest.status !== 'FULFILLED' && (
                    <button
                      onClick={() => handleStatusChange(selectedRequest.requestId, 'FULFILLED')}
                      className="px-4 py-2 rounded-xl text-xs font-bold text-white bg-teal-600 hover:bg-teal-700 shadow"
                    >
                      Mark as Fulfilled
                    </button>
                  )}
                  {selectedRequest.status !== 'CANCELLED' && selectedRequest.status !== 'FULFILLED' && (
                    <button
                      onClick={() => handleStatusChange(selectedRequest.requestId, 'CANCELLED')}
                      className="px-3 py-2 rounded-xl text-xs font-semibold text-slate-600 dark:text-slate-300 hover:bg-slate-100 dark:hover:bg-slate-700"
                    >
                      Cancel Request
                    </button>
                  )}
                </div>

                <Link
                  to={`/find-donors?requestId=${selectedRequest.requestId}`}
                  className="text-xs font-bold text-rose-600 dark:text-rose-400 hover:underline"
                >
                  Search Donors Directly →
                </Link>
              </div>
            </div>
          )}
        </div>
      )}
    </div>
  );
};
