import React, { useState } from 'react';
import { MedicalDisclaimer } from '../components/Disclaimers';
import { PhoneCall, Mail, ShieldAlert, CheckCircle2, Building2 } from 'lucide-react';

export const Contact: React.FC = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [category, setCategory] = useState('General Query');
  const [message, setMessage] = useState('');
  const [submitted, setSubmitted] = useState(false);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setSubmitted(true);
  };

  return (
    <div className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-12 space-y-10">
      <MedicalDisclaimer />

      <div className="text-center max-w-2xl mx-auto space-y-2">
        <h1 className="text-3xl sm:text-4xl font-black text-slate-900 dark:text-white">
          Emergency Helplines & Support
        </h1>
        <p className="text-sm text-slate-500 dark:text-slate-400">
          In an emergency, dial 112 or contact the National Blood Transfusion Helpline directly.
        </p>
      </div>

      {/* Quick Emergency Cards */}
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-6">
        <div className="bg-rose-50 dark:bg-rose-950/40 rounded-3xl p-6 border border-rose-200 dark:border-rose-900/60 space-y-2">
          <PhoneCall className="w-6 h-6 text-rose-600 dark:text-rose-400" />
          <h3 className="font-bold text-slate-900 dark:text-white text-base">National Emergency</h3>
          <p className="text-xs text-slate-600 dark:text-slate-400">All India police, ambulance, and disaster response</p>
          <a href="tel:112" className="text-xl font-black text-rose-600 dark:text-rose-400 block pt-1 hover:underline">
            112
          </a>
        </div>

        <div className="bg-amber-50 dark:bg-amber-950/40 rounded-3xl p-6 border border-amber-200 dark:border-amber-900/60 space-y-2">
          <Building2 className="w-6 h-6 text-amber-600 dark:text-amber-400" />
          <h3 className="font-bold text-slate-900 dark:text-white text-base">National Blood Helpline</h3>
          <p className="text-xs text-slate-600 dark:text-slate-400">Central Government 24/7 blood availability helpline</p>
          <a href="tel:1910" className="text-xl font-black text-amber-600 dark:text-amber-400 block pt-1 hover:underline">
            1910
          </a>
        </div>

        <div className="bg-blue-50 dark:bg-blue-950/40 rounded-3xl p-6 border border-blue-200 dark:border-blue-900/60 space-y-2">
          <ShieldAlert className="w-6 h-6 text-blue-600 dark:text-blue-400" />
          <h3 className="font-bold text-slate-900 dark:text-white text-base">DPDP Grievance Officer</h3>
          <p className="text-xs text-slate-600 dark:text-slate-400">Statutory officer under Digital Personal Data Protection Act</p>
          <a href="mailto:grievance@bloodbridge.in" className="text-xs font-bold text-blue-600 dark:text-blue-400 block pt-2 hover:underline">
            grievance@bloodbridge.in
          </a>
        </div>
      </div>

      {/* Contact Form */}
      <div className="bg-white dark:bg-slate-800 rounded-3xl p-8 border border-slate-200 dark:border-slate-700 max-w-2xl mx-auto shadow-sm">
        {submitted ? (
          <div className="text-center py-6 space-y-3">
            <CheckCircle2 className="w-12 h-12 text-emerald-500 mx-auto" />
            <h3 className="text-xl font-bold text-slate-900 dark:text-white">Message Received</h3>
            <p className="text-xs text-slate-500">
              Our support team will respond to your registered email address within 24 hours.
            </p>
          </div>
        ) : (
          <form onSubmit={handleSubmit} className="space-y-4">
            <h2 className="text-lg font-bold text-slate-900 dark:text-white">Send Us an Inquiry</h2>

            <div>
              <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                Your Full Name
              </label>
              <input
                type="text"
                required
                value={name}
                onChange={(e) => setName(e.target.value)}
                className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-2.5 focus:outline-none"
              />
            </div>

            <div>
              <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                Email Address
              </label>
              <input
                type="email"
                required
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-2.5 focus:outline-none"
              />
            </div>

            <div>
              <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                Category
              </label>
              <select
                value={category}
                onChange={(e) => setCategory(e.target.value)}
                className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-2.5 focus:outline-none"
              >
                <option value="General Query">General Query</option>
                <option value="Hospital Onboarding">Hospital / Blood Bank Partnership</option>
                <option value="Privacy Grievance">DPDP Privacy & Data Erasure Inquiry</option>
                <option value="Abuse Report">Report Fraudulent / Paid Blood Solicitation</option>
              </select>
            </div>

            <div>
              <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                Message Details
              </label>
              <textarea
                rows={4}
                required
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-2.5 focus:outline-none"
              />
            </div>

            <button
              type="submit"
              className="w-full py-3 rounded-xl bg-slate-900 text-white font-bold text-xs shadow hover:bg-slate-800 transition"
            >
              Submit Message
            </button>
          </form>
        )}
      </div>
    </div>
  );
};
