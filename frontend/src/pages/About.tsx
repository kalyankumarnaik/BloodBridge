import React from 'react';
import { MedicalDisclaimer } from '../components/Disclaimers';
import { Heart, ShieldCheck, Users, Target, PhoneCall } from 'lucide-react';

export const About: React.FC = () => {
  return (
    <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-12 space-y-12">
      <MedicalDisclaimer />

      {/* Hero */}
      <div className="text-center max-w-3xl mx-auto space-y-4">
        <span className="text-xs font-bold uppercase tracking-wider text-rose-600 dark:text-rose-400">
          Our Humanitarian Mission
        </span>
        <h1 className="text-4xl font-black text-slate-900 dark:text-white tracking-tight">
          Bridging the Critical Minutes Between Need and Donor in India
        </h1>
        <p className="text-base text-slate-600 dark:text-slate-300 leading-relaxed">
          BloodBridge was conceived with a single, urgent goal: to eliminate frantic panic during medical emergencies
          by connecting families with consenting, compatible voluntary donors near their hospital in minutes.
        </p>
      </div>

      {/* 3 Pillars */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-white dark:bg-slate-800 rounded-3xl p-6 border border-slate-200 dark:border-slate-700 space-y-3">
          <div className="w-12 h-12 rounded-2xl bg-rose-50 dark:bg-rose-950 text-rose-600 dark:text-rose-400 flex items-center justify-center">
            <Heart className="w-6 h-6 fill-current" />
          </div>
          <h2 className="text-lg font-bold text-slate-900 dark:text-white">100% Voluntary & Non-Commercial</h2>
          <p className="text-xs text-slate-600 dark:text-slate-400 leading-relaxed">
            Human blood is a sacred gift, not a commodity. We strictly adhere to National Blood Transfusion Council
            (NBTC) and Supreme Court directives banning commercial blood trade.
          </p>
        </div>

        <div className="bg-white dark:bg-slate-800 rounded-3xl p-6 border border-slate-200 dark:border-slate-700 space-y-3">
          <div className="w-12 h-12 rounded-2xl bg-emerald-50 dark:bg-emerald-950 text-emerald-600 dark:text-emerald-400 flex items-center justify-center">
            <ShieldCheck className="w-6 h-6" />
          </div>
          <h2 className="text-lg font-bold text-slate-900 dark:text-white">Privacy Guarded by Default</h2>
          <p className="text-xs text-slate-600 dark:text-slate-400 leading-relaxed">
            Under India&apos;s DPDP Act 2023, voluntary donors should never suffer spam or unsolicited calls. Exact home
            coordinates and telephone numbers are only unlocked with active consent.
          </p>
        </div>

        <div className="bg-white dark:bg-slate-800 rounded-3xl p-6 border border-slate-200 dark:border-slate-700 space-y-3">
          <div className="w-12 h-12 rounded-2xl bg-blue-50 dark:bg-blue-950 text-blue-600 dark:text-blue-400 flex items-center justify-center">
            <Target className="w-6 h-6" />
          </div>
          <h2 className="text-lg font-bold text-slate-900 dark:text-white">Scientific ABO/Rh Precision</h2>
          <p className="text-xs text-slate-600 dark:text-slate-400 leading-relaxed">
            Our algorithmic dispatch accounts for true red blood cell compatibility (e.g. matching O- donors with
            all recipients, and AB+ recipients with all donors) to maximize response speed.
          </p>
        </div>
      </div>

      {/* Emergency Helpline Callout */}
      <div className="bg-slate-900 text-white rounded-3xl p-8 sm:p-10 flex flex-col md:flex-row items-center justify-between gap-6">
        <div className="space-y-2">
          <h3 className="text-xl font-bold">In Extreme Emergency Situations</h3>
          <p className="text-xs text-slate-300 max-w-lg leading-relaxed">
            Always contact your treating hospital blood bank immediately. For national emergency support, dial{' '}
            <strong className="text-rose-400">112</strong> or the National Blood Helpline at{' '}
            <strong className="text-rose-400">1910</strong>.
          </p>
        </div>
        <a
          href="tel:112"
          className="inline-flex items-center space-x-2 px-6 py-3 rounded-2xl bg-rose-600 hover:bg-rose-700 text-white font-bold text-sm shadow transition"
        >
          <PhoneCall className="w-4 h-4" />
          <span>Call Emergency 112</span>
        </a>
      </div>
    </div>
  );
};
