import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { authApi } from '../services/api';
import { MedicalDisclaimer, PrivacyDisclaimer } from '../components/Disclaimers';
import { User, Mail, Phone, Shield, Trash2, Key, CheckCircle2, AlertTriangle } from 'lucide-react';

export const UserProfile: React.FC = () => {
  const { user, logout } = useAuth();
  const [deleteReason, setDeleteReason] = useState('');
  const [confirmDelete, setConfirmDelete] = useState(false);
  const [deleting, setDeleting] = useState(false);
  const [actionMessage, setActionMessage] = useState<string | null>(null);

  const handleDeleteAccount = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!deleteReason) return;
    setDeleting(true);
    try {
      await authApi.requestAccountDeletion(deleteReason);
      setActionMessage('Your DPDP Act data deletion request has been submitted. Logging out...');
      setTimeout(() => {
        logout();
      }, 2500);
    } catch (err) {
      console.error('Account deletion error:', err);
    } finally {
      setDeleting(false);
    }
  };

  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-6">
      <MedicalDisclaimer />

      <div>
        <h1 className="text-2xl sm:text-3xl font-black text-slate-900 dark:text-white">
          Account & Privacy Settings
        </h1>
        <p className="text-sm text-slate-500 dark:text-slate-400 mt-1">
          Manage your personal details, notification consent, and DPDP Act data protection rights.
        </p>
      </div>

      {actionMessage && (
        <div className="p-4 bg-emerald-50 dark:bg-emerald-950/60 border border-emerald-200 dark:border-emerald-800 rounded-2xl text-xs font-semibold text-emerald-800 dark:text-emerald-200">
          {actionMessage}
        </div>
      )}

      {/* Profile Overview */}
      <div className="bg-white dark:bg-slate-800 rounded-3xl p-6 sm:p-8 border border-slate-200 dark:border-slate-700/80 shadow-sm space-y-4">
        <h2 className="text-base font-bold text-slate-900 dark:text-white border-b border-slate-100 dark:border-slate-700 pb-3">
          Profile Information
        </h2>

        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4 text-xs">
          <div>
            <span className="text-slate-400 block font-medium">Full Name:</span>
            <span className="text-sm font-bold text-slate-900 dark:text-white">{user?.name}</span>
          </div>

          <div>
            <span className="text-slate-400 block font-medium">Email Address:</span>
            <span className="text-sm font-bold text-slate-900 dark:text-white">{user?.email}</span>
          </div>

          <div>
            <span className="text-slate-400 block font-medium">Registered Phone:</span>
            <span className="text-sm font-bold text-slate-900 dark:text-white font-mono">
              {user?.phone || '+91 9876543210'}
            </span>
          </div>

          <div>
            <span className="text-slate-400 block font-medium">System Role:</span>
            <span className="inline-block mt-0.5 px-2.5 py-0.5 rounded-full text-[10px] font-bold bg-rose-100 dark:bg-rose-950 text-rose-700 dark:text-rose-300">
              {user?.role}
            </span>
          </div>
        </div>
      </div>

      {/* Privacy Notice */}
      <PrivacyDisclaimer />

      {/* DPDP Act 2023 - Right to Erasure */}
      <div className="bg-white dark:bg-slate-800 rounded-3xl p-6 sm:p-8 border border-rose-200 dark:border-rose-900/60 shadow-sm space-y-4">
        <div className="flex items-center space-x-2 text-rose-600 dark:text-rose-400">
          <Trash2 className="w-5 h-5" />
          <h2 className="text-base font-bold">DPDP Act 2023: Right to Data Erasure</h2>
        </div>
        <p className="text-xs text-slate-600 dark:text-slate-300 leading-relaxed">
          Under the Digital Personal Data Protection Act (DPDP) 2023, you have the right to request full erasure of
          your personal contact coordinates, profile, and donation preferences from our platform.
        </p>

        {!confirmDelete ? (
          <button
            onClick={() => setConfirmDelete(true)}
            className="px-4 py-2 rounded-xl text-xs font-bold text-rose-600 bg-rose-50 hover:bg-rose-100 dark:bg-rose-950/60 dark:hover:bg-rose-900/60 transition"
          >
            Request Account & Data Deletion
          </button>
        ) : (
          <form onSubmit={handleDeleteAccount} className="space-y-3 pt-2">
            <div className="p-3 bg-rose-50 dark:bg-rose-950/40 rounded-xl border border-rose-200 dark:border-rose-800 text-xs text-rose-800 dark:text-rose-300">
              Warning: This action will permanently remove your donor profile and disable notifications.
            </div>

            <div>
              <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                Reason for Data Deletion Request
              </label>
              <input
                type="text"
                required
                value={deleteReason}
                onChange={(e) => setDeleteReason(e.target.value)}
                placeholder="E.g., Relocating abroad, medical resting, privacy preference"
                className="w-full text-xs rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 p-2.5 text-slate-900 dark:text-white focus:outline-none"
              />
            </div>

            <div className="flex items-center space-x-2">
              <button
                type="button"
                onClick={() => setConfirmDelete(false)}
                className="px-3 py-1.5 rounded-xl text-xs font-semibold text-slate-600 dark:text-slate-300 hover:bg-slate-100"
              >
                Cancel
              </button>
              <button
                type="submit"
                disabled={deleting}
                className="px-4 py-1.5 rounded-xl text-xs font-bold text-white bg-rose-600 hover:bg-rose-700 shadow"
              >
                {deleting ? 'Submitting...' : 'Confirm Erasure Request'}
              </button>
            </div>
          </form>
        )}
      </div>
    </div>
  );
};
