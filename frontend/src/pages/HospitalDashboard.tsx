import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { hospitalApi, requestApi } from '../services/api';
import { BloodGroup, BloodRequest } from '../types';
import { MedicalDisclaimer } from '../components/Disclaimers';
import { BloodGroupBadge, StatusBadge, UrgencyBadge } from '../components/Badges';
import { CardSkeleton } from '../components/LoadingSkeleton';
import {
  Building2,
  ShieldCheck,
  AlertTriangle,
  UploadCloud,
  CheckCircle2,
  Save,
  Clock,
  Plus,
  RefreshCw,
} from 'lucide-react';

export const HospitalDashboard: React.FC = () => {
  const { user } = useAuth();
  const [profile, setProfile] = useState<any>(null);
  const [requests, setRequests] = useState<BloodRequest[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [savingStock, setSavingStock] = useState<boolean>(false);
  const [actionMessage, setActionMessage] = useState<string | null>(null);

  // Stock inventory map
  const [stockMap, setStockMap] = useState<Record<string, number>>({
    O_POSITIVE: 15,
    O_NEGATIVE: 2,
    A_POSITIVE: 10,
    A_NEGATIVE: 3,
    B_POSITIVE: 18,
    B_NEGATIVE: 1,
    AB_POSITIVE: 6,
    AB_NEGATIVE: 0,
  });

  const bloodGroups: BloodGroup[] = [
    'O_NEGATIVE',
    'O_POSITIVE',
    'A_NEGATIVE',
    'A_POSITIVE',
    'B_NEGATIVE',
    'B_POSITIVE',
    'AB_NEGATIVE',
    'AB_POSITIVE',
  ];

  const loadData = async () => {
    setLoading(true);
    try {
      const [profRes, reqsRes] = await Promise.allSettled([
        hospitalApi.getProfile(),
        requestApi.getEmergencyFeed(),
      ]);
      if (profRes.status === 'fulfilled') setProfile(profRes.value);
      if (reqsRes.status === 'fulfilled') setRequests(reqsRes.value);
    } catch (err) {
      console.error('Failed to load hospital dashboard:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleStockChange = (bg: string, delta: number) => {
    setStockMap((prev) => ({
      ...prev,
      [bg]: Math.max(0, (prev[bg] || 0) + delta),
    }));
  };

  const handleSaveStock = async () => {
    setSavingStock(true);
    try {
      // Post updates for each group
      for (const [bg, units] of Object.entries(stockMap)) {
        await hospitalApi.updateAvailability({
          bloodGroup: bg as BloodGroup,
          unitsAvailable: units,
          status: units > 5 ? 'AVAILABLE' : units > 0 ? 'CRITICAL_LOW' : 'UNAVAILABLE',
        });
      }
      setActionMessage('Blood component availability published successfully!');
      setTimeout(() => setActionMessage(null), 3000);
    } catch (err) {
      console.error('Failed to save stock:', err);
    } finally {
      setSavingStock(false);
    }
  };

  const handleVerifyRequest = async (requestId: string) => {
    try {
      await requestApi.verifyRequest(requestId, 'Hospital verified legitimate medical requirement');
      setActionMessage(`Request ${requestId} verified and activated for voluntary donor alerts.`);
      setTimeout(() => setActionMessage(null), 4000);
      loadData();
    } catch (err) {
      console.error('Error verifying request:', err);
    }
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-6">
      <MedicalDisclaimer />

      {/* Header */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <div className="flex items-center space-x-2">
            <h1 className="text-2xl sm:text-3xl font-black text-slate-900 dark:text-white">
              {profile?.hospitalName || user?.name || 'Hospital Administration'}
            </h1>
            <span title="Verified Medical Facility">
              <ShieldCheck className="w-6 h-6 text-emerald-500" />
            </span>
          </div>
          <p className="text-sm text-slate-500 dark:text-slate-400 mt-1">
            License: <span className="font-mono">{profile?.licenseNumber || 'KA-BB-2023-4921'}</span> •{' '}
            {profile?.city || 'Bangalore'}, {profile?.state || 'Karnataka'}
          </p>
        </div>

        <button
          onClick={loadData}
          className="inline-flex items-center space-x-1.5 px-4 py-2 rounded-xl text-xs font-semibold bg-white dark:bg-slate-800 border border-slate-200 dark:border-slate-700 text-slate-700 dark:text-slate-200 hover:bg-slate-50 self-start shadow-sm"
        >
          <RefreshCw className="w-3.5 h-3.5" />
          <span>Refresh Feed</span>
        </button>
      </div>

      {actionMessage && (
        <div className="p-4 bg-emerald-50 dark:bg-emerald-950/60 border border-emerald-200 dark:border-emerald-800 rounded-2xl text-xs font-semibold text-emerald-800 dark:text-emerald-200 shadow-sm">
          {actionMessage}
        </div>
      )}

      {/* Blood Component Inventory Manager */}
      <div className="bg-white dark:bg-slate-800 rounded-3xl p-6 sm:p-8 border border-slate-200 dark:border-slate-700/80 shadow-sm space-y-6">
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4">
          <div>
            <h2 className="text-lg font-bold text-slate-900 dark:text-white">
              Publish Live Blood Component Stock
            </h2>
            <p className="text-xs text-slate-500 dark:text-slate-400">
              Update units available at your hospital blood bank to assist doctors and emergency ambulances.
            </p>
          </div>
          <button
            onClick={handleSaveStock}
            disabled={savingStock}
            className="inline-flex items-center space-x-2 px-6 py-2.5 rounded-xl bg-emerald-600 hover:bg-emerald-700 text-white font-bold text-xs shadow transition disabled:opacity-50"
          >
            <Save className="w-4 h-4" />
            <span>{savingStock ? 'Publishing...' : 'Publish Stock'}</span>
          </button>
        </div>

        <div className="grid grid-cols-2 sm:grid-cols-4 lg:grid-cols-8 gap-3">
          {bloodGroups.map((bg) => {
            const units = stockMap[bg] || 0;
            const isCritical = units <= 2;
            return (
              <div
                key={bg}
                className={`p-3.5 rounded-2xl border text-center flex flex-col justify-between ${
                  isCritical
                    ? 'bg-amber-50 dark:bg-amber-950/40 border-amber-300 dark:border-amber-800'
                    : 'bg-slate-50 dark:bg-slate-700/40 border-slate-200 dark:border-slate-600'
                }`}
              >
                <div>
                  <span className="font-mono font-bold text-sm text-slate-900 dark:text-white block">
                    {bg.replace('_POSITIVE', '+').replace('_NEGATIVE', '-')}
                  </span>
                  <span className="text-[10px] uppercase tracking-wider text-slate-400 block mt-0.5">
                    {isCritical ? 'Critical Low' : 'In Stock'}
                  </span>
                </div>

                <div className="text-2xl font-black text-slate-900 dark:text-white my-2">{units}</div>

                <div className="flex items-center justify-center space-x-1.5">
                  <button
                    onClick={() => handleStockChange(bg, -1)}
                    className="w-7 h-7 rounded-lg bg-white dark:bg-slate-800 border border-slate-300 dark:border-slate-600 font-bold text-slate-700 dark:text-slate-200 hover:bg-slate-100 flex items-center justify-center text-sm"
                  >
                    -
                  </button>
                  <button
                    onClick={() => handleStockChange(bg, 1)}
                    className="w-7 h-7 rounded-lg bg-white dark:bg-slate-800 border border-slate-300 dark:border-slate-600 font-bold text-slate-700 dark:text-slate-200 hover:bg-slate-100 flex items-center justify-center text-sm"
                  >
                    +
                  </button>
                </div>
              </div>
            );
          })}
        </div>
      </div>

      {/* Emergency Requests In Need of Verification */}
      <div className="space-y-4">
        <div>
          <h2 className="text-lg font-bold text-slate-900 dark:text-white">Hospital Emergency Requests</h2>
          <p className="text-xs text-slate-500 dark:text-slate-400">
            Pending blood requests awaiting hospital medical verification to trigger automated donor dispatch.
          </p>
        </div>

        {loading ? (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <CardSkeleton />
            <CardSkeleton />
          </div>
        ) : requests.length === 0 ? (
          <p className="text-xs text-slate-500 italic bg-white dark:bg-slate-800 p-6 rounded-2xl border border-slate-200 dark:border-slate-700 text-center">
            No pending requests for verification. All emergency streams are clear.
          </p>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            {requests.map((req) => (
              <div
                key={req.requestId}
                className="bg-white dark:bg-slate-800 rounded-3xl p-6 border border-slate-200 dark:border-slate-700/80 shadow-sm space-y-4 flex flex-col justify-between"
              >
                <div>
                  <div className="flex items-start justify-between gap-2 mb-2">
                    <div className="flex items-center space-x-3">
                      <BloodGroupBadge bloodGroup={req.bloodGroup} size="md" />
                      <div>
                        <h3 className="font-bold text-slate-900 dark:text-white text-base">
                          {req.patientName}
                        </h3>
                        <span className="text-xs font-mono text-slate-400">ID: {req.requestId}</span>
                      </div>
                    </div>
                    <UrgencyBadge priority={req.priority} />
                  </div>

                  <div className="text-xs text-slate-600 dark:text-slate-300 space-y-1">
                    <p>
                      <strong>Units:</strong> {req.unitsRequired} Units • <strong>Status:</strong> {req.status}
                    </p>
                    <p>
                      <strong>Hospital:</strong> {req.hospitalName}, {req.city}
                    </p>
                    <p className="text-slate-400">Required by: {req.requiredBy}</p>
                  </div>
                </div>

                <div className="pt-3 border-t border-slate-100 dark:border-slate-700 flex items-center justify-between">
                  <StatusBadge status={req.status} />
                  {req.status === 'PENDING' && (
                    <button
                      onClick={() => handleVerifyRequest(req.requestId)}
                      className="px-4 py-2 rounded-xl text-xs font-bold text-white bg-blue-600 hover:bg-blue-700 shadow"
                    >
                      Verify & Alert Donors
                    </button>
                  )}
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};
