import React from 'react';
import { Link } from 'react-router-dom';
import { Heart, Shield, PhoneCall, AlertTriangle, ExternalLink } from 'lucide-react';

export const Footer: React.FC = () => {
  return (
    <footer className="bg-slate-900 text-slate-300 pt-12 pb-8 border-t border-slate-800">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        {/* Critical Medical Disclaimer Banner (PRD Section 8.4) */}
        <div className="bg-amber-950/40 border border-amber-600/40 rounded-xl p-4 sm:p-5 mb-10 text-amber-200">
          <div className="flex items-start space-x-3">
            <AlertTriangle className="w-6 h-6 text-amber-400 flex-shrink-0 mt-0.5" />
            <div className="text-xs sm:text-sm leading-relaxed">
              <span className="font-bold uppercase tracking-wider text-amber-300 block mb-1">
                Mandatory Medical & Emergency Notice
              </span>
              BloodBridge is a connection platform, not a medical service. We do not verify donor health, eligibility,
              or blood compatibility. Always confirm with a qualified medical professional and the treating blood bank.
              In an emergency, contact your hospital immediately or call{' '}
              <a href="tel:112" className="font-bold underline text-white hover:text-amber-300">
                112
              </a>
              . BloodBridge strictly prohibits any buying, selling, or commercial exchange of blood.
            </div>
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-8 mb-10">
          {/* Brand Info */}
          <div className="lg:col-span-2 space-y-4">
            <div className="flex items-center space-x-2">
              <div className="w-9 h-9 rounded-xl bg-gradient-to-tr from-rose-600 to-red-500 flex items-center justify-center text-white shadow-lg shadow-rose-900/30">
                <Heart className="w-5 h-5 fill-white" />
              </div>
              <span className="text-xl font-black tracking-tight text-white">
                Blood<span className="text-rose-500">Bridge</span>
              </span>
            </div>
            <p className="text-sm text-slate-400 max-w-sm leading-relaxed">
              India&apos;s smart emergency blood donation connection network. Fast, privacy-first, and verified
              donor matching when every minute counts.
            </p>
            <div className="flex flex-wrap items-center gap-3 pt-2">
              <div className="inline-flex items-center space-x-2 px-3 py-1.5 rounded-lg bg-slate-800/80 border border-slate-700/60 text-xs font-medium text-slate-300">
                <Shield className="w-4 h-4 text-emerald-400" />
                <span>DPDP Act 2023 Compliant</span>
              </div>
              <div className="inline-flex items-center space-x-2 px-3 py-1.5 rounded-lg bg-rose-950/60 border border-rose-800/40 text-xs font-semibold text-rose-300">
                <PhoneCall className="w-4 h-4 text-rose-400" />
                <span>Emergency: 112 / 1910</span>
              </div>
            </div>
          </div>

          {/* Quick Links */}
          <div>
            <h3 className="text-xs font-semibold uppercase tracking-wider text-slate-400 mb-4">Quick Links</h3>
            <ul className="space-y-2.5 text-sm">
              <li>
                <Link to="/find-donors" className="hover:text-white transition-colors">
                  Find Donors
                </Link>
              </li>
              <li>
                <Link to="/emergency-request" className="text-rose-400 hover:text-rose-300 font-medium transition-colors">
                  Post Emergency Request
                </Link>
              </li>
              <li>
                <Link to="/compatibility-guide" className="hover:text-white transition-colors">
                  Compatibility Matrix
                </Link>
              </li>
              <li>
                <Link to="/blood-banks" className="hover:text-white transition-colors">
                  Verified Blood Banks
                </Link>
              </li>
              <li>
                <Link to="/how-it-works" className="hover:text-white transition-colors">
                  How It Works
                </Link>
              </li>
            </ul>
          </div>

          {/* User Portals */}
          <div>
            <h3 className="text-xs font-semibold uppercase tracking-wider text-slate-400 mb-4">Portals</h3>
            <ul className="space-y-2.5 text-sm">
              <li>
                <Link to="/register-donor" className="hover:text-white transition-colors">
                  Register as Voluntary Donor
                </Link>
              </li>
              <li>
                <Link to="/hospital-register" className="hover:text-white transition-colors">
                  Hospital Onboarding
                </Link>
              </li>
              <li>
                <Link to="/login" className="hover:text-white transition-colors">
                  Sign In / Dashboard
                </Link>
              </li>
              <li>
                <Link to="/about" className="hover:text-white transition-colors">
                  About BloodBridge
                </Link>
              </li>
              <li>
                <Link to="/contact" className="hover:text-white transition-colors">
                  Contact & Helplines
                </Link>
              </li>
            </ul>
          </div>

          {/* Emergency & Helplines (India) */}
          <div>
            <h3 className="text-xs font-semibold uppercase tracking-wider text-rose-400 mb-4">India Helplines</h3>
            <ul className="space-y-2 text-xs text-slate-400">
              <li>
                <span className="text-slate-300 font-medium block">National Emergency:</span>
                <a href="tel:112" className="text-rose-400 hover:underline font-bold text-sm">112</a>
              </li>
              <li>
                <span className="text-slate-300 font-medium block">National Blood Helpline:</span>
                <a href="tel:1910" className="text-rose-400 hover:underline font-bold text-sm">1910</a>
              </li>
              <li>
                <span className="text-slate-300 font-medium block">Indian Red Cross Blood Bank:</span>
                <span className="text-slate-300">011-23716441</span>
              </li>
              <li className="pt-2">
                <a
                  href="https://eraktkosh.mohfw.gov.in"
                  target="_blank"
                  rel="noreferrer"
                  className="inline-flex items-center space-x-1 text-slate-400 hover:text-white text-xs"
                >
                  <span>e-RaktKosh Portal</span>
                  <ExternalLink className="w-3 h-3" />
                </a>
              </li>
            </ul>
          </div>
        </div>

        {/* Bottom Bar */}
        <div className="pt-8 border-t border-slate-800 text-xs text-slate-500 flex flex-col sm:flex-row items-center justify-between gap-4">
          <p>© {new Date().getFullYear()} BloodBridge India. All rights reserved. Built for emergency humanitarian aid.</p>
          <div className="flex items-center space-x-6">
            <Link to="/privacy-policy" className="hover:text-slate-300 transition-colors">
              Privacy Policy (DPDP Act)
            </Link>
            <Link to="/terms" className="hover:text-slate-300 transition-colors">
              Terms & Conditions
            </Link>
            <Link to="/contact" className="hover:text-slate-300 transition-colors">
              Grievance Officer
            </Link>
          </div>
        </div>
      </div>
    </footer>
  );
};
