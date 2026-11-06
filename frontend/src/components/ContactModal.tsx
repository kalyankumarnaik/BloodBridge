import React, { useState } from 'react';
import { DonorSearchResult } from '../types';
import { donorApi } from '../services/api';
import { BloodGroupBadge } from './Badges';
import { X, ShieldAlert, Send, CheckCircle2 } from 'lucide-react';

interface ContactModalProps {
  donor: DonorSearchResult | null;
  onClose: () => void;
  onSuccess?: () => void;
}

export const ContactModal: React.FC<ContactModalProps> = ({ donor, onClose, onSuccess }) => {
  const [reason, setReason] = useState('');
  const [consentChecked, setConsentChecked] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [submitted, setSubmitted] = useState(false);

  if (!donor) return null;

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!consentChecked) {
      setError('You must confirm consent and emergency purpose acknowledgment.');
      return;
    }
    if (reason.trim().length < 10) {
      setError('Please provide at least 10 characters explaining the patient requirement and hospital.');
      return;
    }

    setLoading(true);
    setError(null);

    try {
      await donorApi.requestContact({
        donorUuid: donor.donorUuid,
        reason: reason.trim(),
      });
      setSubmitted(true);
      if (onSuccess) onSuccess();
    } catch (err: any) {
      setError(
        err.response?.data?.message ||
          'Failed to send contact request. Please ensure you are logged in or have an active emergency request.'
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="fixed inset-0 z-50 overflow-y-auto bg-slate-900/60 backdrop-blur-sm flex items-center justify-center p-4">
      <div className="relative bg-white dark:bg-slate-800 rounded-3xl max-w-lg w-full p-6 shadow-2xl border border-slate-200 dark:border-slate-700">
        <button
          onClick={onClose}
          className="absolute top-5 right-5 text-slate-400 hover:text-slate-600 dark:hover:text-slate-200 p-1"
        >
          <X className="w-5 h-5" />
        </button>

        {submitted ? (
          <div className="py-6 text-center space-y-4">
            <div className="w-14 h-14 bg-emerald-100 dark:bg-emerald-950/80 text-emerald-600 dark:text-emerald-400 rounded-2xl flex items-center justify-center mx-auto">
              <CheckCircle2 className="w-8 h-8" />
            </div>
            <h3 className="text-xl font-bold text-slate-900 dark:text-white">Emergency Request Sent!</h3>
            <p className="text-sm text-slate-600 dark:text-slate-300 max-w-sm mx-auto leading-relaxed">
              We have alerted <span className="font-semibold">{donor.displayName}</span> via SMS and priority in-app
              notification. If the donor accepts and provides consent, their contact details will appear in your
              dashboard.
            </p>
            <button
              onClick={onClose}
              className="mt-4 px-6 py-2.5 rounded-xl bg-slate-900 dark:bg-white text-white dark:text-slate-900 font-bold text-sm shadow hover:opacity-90 transition"
            >
              Done
            </button>
          </div>
        ) : (
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <span className="text-xs font-bold uppercase tracking-wider text-rose-600 dark:text-rose-400">
                Contact Request
              </span>
              <h2 className="text-lg font-bold text-slate-900 dark:text-white mt-0.5">
                Request Voluntary Donor Consent
              </h2>
            </div>

            {/* Donor Summary */}
            <div className="flex items-center justify-between p-3.5 bg-slate-50 dark:bg-slate-700/40 rounded-2xl border border-slate-100 dark:border-slate-700">
              <div className="flex items-center space-x-3">
                <BloodGroupBadge bloodGroup={donor.bloodGroup} size="md" />
                <div>
                  <p className="text-sm font-bold text-slate-900 dark:text-white">{donor.displayName}</p>
                  <p className="text-xs text-slate-500 dark:text-slate-400">
                    {donor.city}, {donor.state} • {donor.distanceBand}
                  </p>
                </div>
              </div>
            </div>

            {/* Medical / Legal Warning */}
            <div className="bg-amber-50 dark:bg-amber-950/40 border border-amber-200 dark:border-amber-700/60 p-3 rounded-xl flex items-start space-x-2.5 text-xs text-amber-800 dark:text-amber-300">
              <ShieldAlert className="w-4 h-4 flex-shrink-0 mt-0.5 text-amber-600 dark:text-amber-400" />
              <span>
                Under the Indian Blood Safety Regulations & DPDP Act 2023, donor contact details are confidential.
                Voluntary donors can accept or decline this request without liability.
              </span>
            </div>

            {error && (
              <div className="p-3 text-xs bg-rose-50 dark:bg-rose-950/60 border border-rose-200 dark:border-rose-800 rounded-xl text-rose-700 dark:text-rose-300">
                {error}
              </div>
            )}

            {/* Form Fields */}
            <div>
              <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                Emergency Details & Hospital Name <span className="text-rose-500">*</span>
              </label>
              <textarea
                value={reason}
                onChange={(e) => setReason(e.target.value)}
                rows={3}
                required
                placeholder="E.g., Urgent requirement for patient undergoing cardiac surgery at Apollo Hospital Bangalore. Ward 4."
                className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-3 focus:ring-2 focus:ring-rose-500 focus:outline-none"
              />
            </div>

            {/* DPDP Consent */}
            <label className="flex items-start space-x-2 text-xs text-slate-600 dark:text-slate-300 cursor-pointer">
              <input
                type="checkbox"
                checked={consentChecked}
                onChange={(e) => setConsentChecked(e.target.checked)}
                className="mt-0.5 rounded text-rose-600 focus:ring-rose-500"
              />
              <span>
                I confirm this request is solely for a legitimate patient emergency and that my identity details will
                be logged in the immutable audit registry.
              </span>
            </label>

            <div className="flex items-center justify-end space-x-3 pt-2">
              <button
                type="button"
                onClick={onClose}
                className="px-4 py-2 rounded-xl text-xs font-semibold text-slate-600 dark:text-slate-300 hover:bg-slate-100 dark:hover:bg-slate-700"
              >
                Cancel
              </button>
              <button
                type="submit"
                disabled={loading}
                className="inline-flex items-center space-x-1.5 px-5 py-2.5 rounded-xl text-xs font-bold text-white bg-rose-600 hover:bg-rose-700 disabled:opacity-50 transition shadow"
              >
                {loading ? (
                  <span>Sending...</span>
                ) : (
                  <>
                    <Send className="w-3.5 h-3.5" />
                    <span>Send Notification</span>
                  </>
                )}
              </button>
            </div>
          </form>
        )}
      </div>
    </div>
  );
};
