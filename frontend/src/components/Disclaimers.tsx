import React, { useState } from 'react';
import { AlertTriangle, ShieldCheck, X } from 'lucide-react';

export const MedicalDisclaimer: React.FC<{ dismissible?: boolean; className?: string }> = ({
  dismissible = false,
  className = '',
}) => {
  const [dismissed, setDismissed] = useState(false);
  if (dismissed) return null;

  return (
    <div
      className={`bg-amber-50 dark:bg-amber-950/40 border border-amber-300 dark:border-amber-700/60 rounded-xl p-4 text-amber-900 dark:text-amber-200 transition-all ${className}`}
      role="alert"
    >
      <div className="flex items-start space-x-3">
        <AlertTriangle className="w-5 h-5 text-amber-600 dark:text-amber-400 flex-shrink-0 mt-0.5" />
        <div className="text-xs sm:text-sm leading-relaxed flex-1">
          <strong className="font-semibold block mb-0.5">Medical Safety & Transfusion Notice (PRD §8.4):</strong>
          BloodBridge is a connection platform, not a medical service. We do not verify donor health, eligibility or
          blood compatibility. Always confirm with a qualified medical professional and the treating blood bank. In an
          emergency, contact your hospital or call{' '}
          <a href="tel:112" className="underline font-bold text-amber-800 dark:text-amber-300">
            112
          </a>
          .
        </div>
        {dismissible && (
          <button
            onClick={() => setDismissed(true)}
            aria-label="Dismiss disclaimer"
            className="text-amber-600 dark:text-amber-400 hover:text-amber-800 dark:hover:text-amber-200 p-1"
          >
            <X className="w-4 h-4" />
          </button>
        )}
      </div>
    </div>
  );
};

export const PrivacyDisclaimer: React.FC<{ className?: string }> = ({ className = '' }) => {
  return (
    <div
      className={`bg-slate-50 dark:bg-slate-800/60 border border-slate-200 dark:border-slate-700/80 rounded-xl p-4 text-slate-700 dark:text-slate-300 text-xs sm:text-sm ${className}`}
    >
      <div className="flex items-start space-x-3">
        <ShieldCheck className="w-5 h-5 text-emerald-600 dark:text-emerald-400 flex-shrink-0 mt-0.5" />
        <div className="leading-relaxed">
          <strong className="font-semibold text-slate-900 dark:text-white block mb-0.5">
            Privacy Protected by Default:
          </strong>
          To safeguard donor dignity and prevent spam, donor phone numbers, email addresses, and exact residential
          coordinates are never revealed publicly. Contact information is only shared after an explicit, unexpired
          consent response to a verified emergency request.
        </div>
      </div>
    </div>
  );
};
