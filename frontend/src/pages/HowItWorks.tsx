import React from 'react';
import { Link } from 'react-router-dom';
import { MedicalDisclaimer } from '../components/Disclaimers';
import { AlertCircle, Search, ShieldCheck, Heart, Hospital, CheckCircle2 } from 'lucide-react';

export const HowItWorks: React.FC = () => {
  return (
    <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-12 space-y-12">
      <MedicalDisclaimer />

      <div className="text-center max-w-3xl mx-auto space-y-4">
        <span className="text-xs font-bold uppercase tracking-wider text-rose-600 dark:text-rose-400">
          Step-by-Step Architecture
        </span>
        <h1 className="text-4xl font-black text-slate-900 dark:text-white tracking-tight">
          How BloodBridge Connects Donors & Patients
        </h1>
        <p className="text-base text-slate-600 dark:text-slate-300 leading-relaxed">
          Designed with doctors, blood bank officers, and voluntary donors to ensure zero delay, clinical accuracy, and
          complete privacy protection.
        </p>
      </div>

      {/* Step by Step Breakdown */}
      <div className="space-y-8">
        <div className="bg-white dark:bg-slate-800 rounded-3xl p-8 border border-slate-200 dark:border-slate-700 space-y-4">
          <div className="flex items-center space-x-3 text-rose-600 dark:text-rose-400">
            <div className="w-10 h-10 rounded-2xl bg-rose-100 dark:bg-rose-950 flex items-center justify-center font-black text-lg">
              1
            </div>
            <h2 className="text-xl font-bold text-slate-900 dark:text-white">Request Registration & Triage</h2>
          </div>
          <p className="text-sm text-slate-600 dark:text-slate-300 leading-relaxed pl-13">
            The patient or family submits the required blood group, hospital location, PIN code, and required units. A
            unique tracking ID (`BB-YYYYMMDD-XXXXXX`) is issued, and an SHA-256 fingerprint check prevents duplicate
            spam postings.
          </p>
        </div>

        <div className="bg-white dark:bg-slate-800 rounded-3xl p-8 border border-slate-200 dark:border-slate-700 space-y-4">
          <div className="flex items-center space-x-3 text-rose-600 dark:text-rose-400">
            <div className="w-10 h-10 rounded-2xl bg-rose-100 dark:bg-rose-950 flex items-center justify-center font-black text-lg">
              2
            </div>
            <h2 className="text-xl font-bold text-slate-900 dark:text-white">
              Hospital Medical Verification
            </h2>
          </div>
          <p className="text-sm text-slate-600 dark:text-slate-300 leading-relaxed pl-13">
            Registered partner hospitals or platform administrators review the request details. Once verified, the
            state machine transitions the status from `PENDING` to `ACTIVE`, initiating notification waves.
          </p>
        </div>

        <div className="bg-white dark:bg-slate-800 rounded-3xl p-8 border border-slate-200 dark:border-slate-700 space-y-4">
          <div className="flex items-center space-x-3 text-rose-600 dark:text-rose-400">
            <div className="w-10 h-10 rounded-2xl bg-rose-100 dark:bg-rose-950 flex items-center justify-center font-black text-lg">
              3
            </div>
            <h2 className="text-xl font-bold text-slate-900 dark:text-white">
              Algorithmic ABO/Rh & Distance Matching
            </h2>
          </div>
          <p className="text-sm text-slate-600 dark:text-slate-300 leading-relaxed pl-13">
            The matching engine queries voluntary donors within the target radius using the Haversine formula and checks
            red blood cell compatibility. Eligible donors receive high-priority SMS and in-app alerts without exposing
            their phone numbers publicly.
          </p>
        </div>

        <div className="bg-white dark:bg-slate-800 rounded-3xl p-8 border border-slate-200 dark:border-slate-700 space-y-4">
          <div className="flex items-center space-x-3 text-rose-600 dark:text-rose-400">
            <div className="w-10 h-10 rounded-2xl bg-rose-100 dark:bg-rose-950 flex items-center justify-center font-black text-lg">
              4
            </div>
            <h2 className="text-xl font-bold text-slate-900 dark:text-white">
              Consent-Based Mutual Contact Unlock
            </h2>
          </div>
          <p className="text-sm text-slate-600 dark:text-slate-300 leading-relaxed pl-13">
            When a voluntary donor accepts the alert, mutual phone coordinates are unlocked. The donor proceeds to the
            hospital blood bank where standard laboratory cross-matching and transfusion take place.
          </p>
        </div>
      </div>

      <div className="text-center pt-4">
        <Link
          to="/emergency-request"
          className="inline-flex items-center space-x-2 px-8 py-4 rounded-2xl bg-rose-600 hover:bg-rose-700 text-white font-bold text-sm shadow-lg transition"
        >
          <AlertCircle className="w-4 h-4" />
          <span>Post an Emergency Request Now</span>
        </Link>
      </div>
    </div>
  );
};
