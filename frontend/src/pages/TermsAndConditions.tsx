import React from 'react';
import { MedicalDisclaimer } from '../components/Disclaimers';
import { ShieldAlert, AlertTriangle } from 'lucide-react';

export const TermsAndConditions: React.FC = () => {
  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-12 space-y-8">
      <MedicalDisclaimer />

      <div>
        <span className="text-xs font-bold uppercase tracking-wider text-rose-600 dark:text-rose-400">
          User Agreement
        </span>
        <h1 className="text-3xl font-black text-slate-900 dark:text-white mt-1">Terms and Conditions</h1>
        <p className="text-xs text-slate-500 dark:text-slate-400 mt-1">
          Effective Date: 2026 • Governing Law: Republic of India
        </p>
      </div>

      <div className="bg-white dark:bg-slate-800 rounded-3xl p-8 border border-slate-200 dark:border-slate-700 space-y-6 text-sm text-slate-700 dark:text-slate-300 leading-relaxed">
        <section className="space-y-2">
          <h2 className="text-base font-bold text-slate-900 dark:text-white">
            1. Strict Prohibition of Commercial Blood Trade
          </h2>
          <p>
            Under the Drugs and Cosmetics Act, 1940 and Supreme Court directives (Common Cause v. Union of India, 1996),
            the commercial buying, selling, or financial brokering of human blood in India is a cognizable criminal
            offense. BloodBridge is strictly a voluntary connection intermediary. Any user soliciting or offering money
            will face permanent account termination and referral to law enforcement agencies.
          </p>
        </section>

        <section className="space-y-2">
          <h2 className="text-base font-bold text-slate-900 dark:text-white">
            2. Intermediary Status & No Medical Warranty
          </h2>
          <p>
            BloodBridge operates strictly as an intermediary communication platform connecting willing voluntary
            donors with patients and hospitals. BloodBridge does NOT:
          </p>
          <ul className="list-disc pl-5 space-y-1 text-xs">
            <li>Perform medical eligibility or health screenings on voluntary donors.</li>
            <li>Certify blood safety, cross-matching, or disease screening.</li>
            <li>Guarantee blood availability or donor responsiveness in critical conditions.</li>
          </ul>
          <p className="text-xs">
            All medical determinations, transfusion compatibility tests, serological cross-matchings, and blood banking
            procedures must be performed independently by licensed healthcare professionals at the treating hospital.
          </p>
        </section>

        <section className="space-y-2">
          <h2 className="text-base font-bold text-slate-900 dark:text-white">3. Emergency Situations</h2>
          <p>
            BloodBridge is designed to supplement, not replace, institutional hospital emergency workflows. In life-critical
            scenarios, users MUST contact emergency medical services (112) or the treating hospital emergency room
            without delay.
          </p>
        </section>

        <section className="space-y-2">
          <h2 className="text-base font-bold text-slate-900 dark:text-white">4. Jurisdiction</h2>
          <p>
            These terms are governed by the laws of India. Any legal dispute or proceeding arising out of or related to
            the platform shall be subject to the exclusive jurisdiction of the competent courts in India.
          </p>
        </section>
      </div>
    </div>
  );
};
