import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { MedicalDisclaimer } from '../components/Disclaimers';
import { Award, Calendar, Heart, Download, ShieldCheck, CheckCircle2 } from 'lucide-react';

interface HistoryItem {
  id: number;
  date: string;
  hospitalName: string;
  city: string;
  units: number;
  bloodGroup: string;
  certificateId: string;
}

export const DonationHistory: React.FC = () => {
  const { user } = useAuth();
  const [selectedCert, setSelectedCert] = useState<HistoryItem | null>(null);

  const historyRecords: HistoryItem[] = [
    {
      id: 1,
      date: '15 Jan 2026',
      hospitalName: 'Manipal Hospital Blood Center',
      city: 'Bangalore',
      units: 1,
      bloodGroup: 'O+',
      certificateId: 'BB-CERT-2026-0041',
    },
    {
      id: 2,
      date: '10 Oct 2025',
      hospitalName: 'Apollo Hospital Transfusion Medicine',
      city: 'Bangalore',
      units: 1,
      bloodGroup: 'O+',
      certificateId: 'BB-CERT-2025-9924',
    },
    {
      id: 3,
      date: '12 Jun 2025',
      hospitalName: 'St. John’s Medical College Blood Bank',
      city: 'Bangalore',
      units: 1,
      bloodGroup: 'O+',
      certificateId: 'BB-CERT-2025-4182',
    },
  ];

  return (
    <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-6">
      <MedicalDisclaimer />

      <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
        <div>
          <h1 className="text-2xl sm:text-3xl font-black text-slate-900 dark:text-white">
            My Blood Donation History
          </h1>
          <p className="text-sm text-slate-500 dark:text-slate-400 mt-1">
            Track your humanitarian milestones, past donations, and digital certificates of appreciation.
          </p>
        </div>

        {/* Milestone Pill */}
        <div className="inline-flex items-center space-x-2 px-4 py-2 rounded-2xl bg-amber-50 dark:bg-amber-950/60 border border-amber-300 dark:border-amber-800 text-amber-800 dark:text-amber-200 text-xs font-bold self-start">
          <Award className="w-4 h-4 text-amber-500" />
          <span>Milestone: Bronze Lifesaver (3 Donations)</span>
        </div>
      </div>

      {/* History Table */}
      <div className="bg-white dark:bg-slate-800 rounded-3xl border border-slate-200 dark:border-slate-700/80 overflow-hidden shadow-sm">
        <div className="p-5 border-b border-slate-100 dark:border-slate-700 font-bold text-sm text-slate-900 dark:text-white">
          Verified Donation Records
        </div>
        <div className="overflow-x-auto">
          <table className="w-full text-xs text-left">
            <thead className="bg-slate-50 dark:bg-slate-700/30 text-slate-500 uppercase tracking-wider text-[11px]">
              <tr>
                <th className="p-4">Date</th>
                <th className="p-4">Hospital / Blood Bank</th>
                <th className="p-4">Location</th>
                <th className="p-4">Blood Group</th>
                <th className="p-4">Units</th>
                <th className="p-4 text-right">Certificate</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-slate-100 dark:divide-slate-700">
              {historyRecords.map((item) => (
                <tr key={item.id} className="hover:bg-slate-50 dark:hover:bg-slate-700/30 transition">
                  <td className="p-4 font-semibold text-slate-900 dark:text-white flex items-center space-x-2">
                    <Calendar className="w-3.5 h-3.5 text-rose-500" />
                    <span>{item.date}</span>
                  </td>
                  <td className="p-4 text-slate-700 dark:text-slate-300">{item.hospitalName}</td>
                  <td className="p-4 text-slate-500">{item.city}</td>
                  <td className="p-4 font-mono font-bold text-rose-600 dark:text-rose-400">{item.bloodGroup}</td>
                  <td className="p-4 font-semibold text-slate-700 dark:text-slate-300">{item.units} Unit</td>
                  <td className="p-4 text-right">
                    <button
                      onClick={() => setSelectedCert(item)}
                      className="inline-flex items-center space-x-1 px-3 py-1.5 rounded-lg text-xs font-semibold bg-rose-50 dark:bg-rose-950 text-rose-700 dark:text-rose-300 hover:bg-rose-100 transition"
                    >
                      <Download className="w-3.5 h-3.5" />
                      <span>View</span>
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {/* Certificate Modal */}
      {selectedCert && (
        <div className="fixed inset-0 z-50 bg-slate-900/60 backdrop-blur-sm flex items-center justify-center p-4">
          <div className="bg-white dark:bg-slate-800 rounded-3xl max-w-xl w-full p-8 border-4 border-amber-400 shadow-2xl relative space-y-6 text-center">
            <div className="w-16 h-16 rounded-full bg-rose-50 dark:bg-rose-950 text-rose-600 dark:text-rose-400 mx-auto flex items-center justify-center">
              <Award className="w-10 h-10" />
            </div>

            <div>
              <span className="text-[11px] font-bold uppercase tracking-widest text-amber-600 dark:text-amber-400">
                BloodBridge Humanitarian Honor
              </span>
              <h2 className="text-2xl font-black text-slate-900 dark:text-white mt-1">
                Certificate of Voluntary Blood Donation
              </h2>
            </div>

            <p className="text-xs sm:text-sm text-slate-600 dark:text-slate-300 leading-relaxed max-w-md mx-auto">
              This certificate is proudly awarded to <strong className="text-slate-900 dark:text-white">{user?.name}</strong>{' '}
              in grateful recognition of their voluntary blood donation at{' '}
              <strong className="text-slate-900 dark:text-white">{selectedCert.hospitalName}</strong> on{' '}
              {selectedCert.date}.
            </p>

            <div className="p-4 bg-slate-50 dark:bg-slate-700/50 rounded-2xl border border-slate-200 dark:border-slate-600 font-mono text-xs flex justify-between">
              <span>Cert No: {selectedCert.certificateId}</span>
              <span>Blood Group: {selectedCert.bloodGroup}</span>
            </div>

            <button
              onClick={() => setSelectedCert(null)}
              className="px-6 py-2.5 rounded-xl bg-slate-900 text-white font-bold text-xs"
            >
              Close Certificate
            </button>
          </div>
        </div>
      )}
    </div>
  );
};
