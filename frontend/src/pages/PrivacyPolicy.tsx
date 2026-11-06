import React from 'react';
import { MedicalDisclaimer } from '../components/Disclaimers';
import { Shield, Lock, Eye, FileText } from 'lucide-react';

export const PrivacyPolicy: React.FC = () => {
  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-12 space-y-8">
      <MedicalDisclaimer />

      <div>
        <span className="text-xs font-bold uppercase tracking-wider text-rose-600 dark:text-rose-400">
          Statutory Compliance
        </span>
        <h1 className="text-3xl font-black text-slate-900 dark:text-white mt-1">
          Privacy Policy (DPDP Act 2023)
        </h1>
        <p className="text-xs text-slate-500 dark:text-slate-400 mt-1">
          Last Updated: 2026 • Compliant with the Digital Personal Data Protection Act, 2023 (India)
        </p>
      </div>

      <div className="bg-white dark:bg-slate-800 rounded-3xl p-8 border border-slate-200 dark:border-slate-700 space-y-6 text-sm text-slate-700 dark:text-slate-300 leading-relaxed">
        <section className="space-y-2">
          <h2 className="text-base font-bold text-slate-900 dark:text-white">1. Data Fiduciary Identity</h2>
          <p>
            BloodBridge India operates as a Data Fiduciary under Section 2(i) of the Digital Personal Data Protection
            Act (DPDP), 2023. Our purpose is solely to facilitate voluntary, non-commercial blood donor connections
            during medical emergencies.
          </p>
        </section>

        <section className="space-y-2">
          <h2 className="text-base font-bold text-slate-900 dark:text-white">2. Voluntary Donor Privacy by Default</h2>
          <p>
            We strictly protect the personal coordinates of our voluntary donors. Public search results never display
            exact residential addresses, full surnames, or telephone numbers. Donors are presented with anonymized
            distance bands (e.g., &quot;&lt; 2 km&quot;) and first names with initials only.
          </p>
        </section>

        <section className="space-y-2">
          <h2 className="text-base font-bold text-slate-900 dark:text-white">3. Consent Architecture</h2>
          <p>
            Contact information is disclosed only when a registered voluntary donor explicitly accepts an emergency
            request or consents to a contact request from a verified requester. Consents can be withdrawn at any time
            through the profile management settings.
          </p>
        </section>

        <section className="space-y-2">
          <h2 className="text-base font-bold text-slate-900 dark:text-white">4. Right to Erasure & Access</h2>
          <p>
            Under Section 12 of the DPDP Act 2023, data principals retain the statutory right to review, update, or
            permanently erase their personal data from our servers. A self-serve data erasure request mechanism is
            accessible via the User Profile tab.
          </p>
        </section>

        <section className="space-y-2">
          <h2 className="text-base font-bold text-slate-900 dark:text-white">5. Statutory Grievance Redressal</h2>
          <p>
            In compliance with Indian IT and DPDP regulations, for any data protection grievances, please contact:
            <br />
            <strong>Grievance Redressal Officer:</strong> DPDP Compliance Cell, BloodBridge India
            <br />
            <strong>Email:</strong> grievance@bloodbridge.in
          </p>
        </section>
      </div>
    </div>
  );
};
