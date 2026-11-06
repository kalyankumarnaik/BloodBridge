import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import {
  Heart,
  Search,
  AlertCircle,
  ShieldCheck,
  Clock,
  Building2,
  Users,
  ChevronRight,
  ArrowRight,
  Sparkles,
  HelpCircle,
  ChevronDown,
  ChevronUp,
} from 'lucide-react';
import { MedicalDisclaimer } from '../components/Disclaimers';

export const Home: React.FC = () => {
  const [activeFaq, setActiveFaq] = useState<number | null>(null);

  const stats = [
    { label: 'Voluntary Donors', value: '12,500+', icon: Users, desc: 'Registered across Indian cities' },
    { label: 'Avg. Match Time', value: '< 15 mins', icon: Clock, desc: 'Fast emergency contact notifications' },
    { label: 'Partner Hospitals', value: '180+', icon: Building2, desc: 'Verified healthcare institutions' },
    { label: 'Successful Matches', value: '8,400+', icon: Heart, desc: 'Critical emergencies assisted' },
  ];

  const faqs = [
    {
      q: 'How does BloodBridge safeguard donor privacy?',
      a: 'We strictly follow the Digital Personal Data Protection (DPDP) Act 2023. When requesters search for donors, they only see first names, blood group, approximate distance bands, and city. Phone numbers and exact coordinates are never shared until a donor explicitly reviews and accepts an emergency request.',
    },
    {
      q: 'Does BloodBridge charge any fee for blood requests?',
      a: 'No, BloodBridge is 100% free and voluntary. The National Blood Transfusion Council (NBTC) regulations strictly forbid commercial buying or selling of human blood. Any user requesting or offering money will be permanently banned and reported to law enforcement.',
    },
    {
      q: 'How are emergency blood requests verified?',
      a: 'Requests can be verified by registered partner hospitals or our administrative verification team. Verifications help ensure genuine medical necessity and prevent spam or duplicate alerts to voluntary donors.',
    },
    {
      q: 'How often can voluntary donors donate blood in India?',
      a: 'In accordance with Indian health guidelines, male donors can donate whole blood every 90 days (3 months), and female donors every 120 days (4 months). BloodBridge tracks donation intervals and prevents notification fatigue during your resting period.',
    },
    {
      q: 'What should I do in an extreme life-or-death emergency?',
      a: 'Immediately contact your hospital’s in-house blood bank, call the National Emergency Helpline 112, or the National Blood Helpline 1910. BloodBridge connects voluntary donors to assist families, but should be used in coordination with hospital emergency staff.',
    },
  ];

  return (
    <div className="space-y-16 pb-16">
      {/* Top Notice */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 pt-6">
        <MedicalDisclaimer />
      </div>

      {/* Hero Section */}
      <section className="relative overflow-hidden bg-gradient-to-b from-rose-50/70 via-white to-slate-50 dark:from-slate-900 dark:via-slate-900 dark:to-slate-950 py-12 sm:py-20">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 relative z-10">
          <div className="text-center max-w-3xl mx-auto space-y-6">
            <div className="inline-flex items-center space-x-2 px-3.5 py-1.5 rounded-full bg-rose-100 dark:bg-rose-950/80 border border-rose-300 dark:border-rose-800 text-rose-700 dark:text-rose-300 text-xs font-bold tracking-wide uppercase">
              <Sparkles className="w-4 h-4 text-rose-500" />
              <span>Smart Emergency Blood Donor Network India</span>
            </div>

            <h1 className="text-4xl sm:text-5xl lg:text-6xl font-black text-slate-900 dark:text-white tracking-tight leading-[1.15]">
              Connecting <span className="text-rose-600 dark:text-rose-500">Lifesavers</span> When Every Second Counts
            </h1>

            <p className="text-base sm:text-lg text-slate-600 dark:text-slate-300 max-w-2xl mx-auto leading-relaxed">
              Find voluntary, verified blood donors nearby in minutes. Privacy-protected, consent-based, and zero
              commercial bias. Built for patients, families, and healthcare workers across India.
            </p>

            <div className="flex flex-col sm:flex-row items-center justify-center gap-4 pt-4">
              <Link
                to="/emergency-request"
                className="w-full sm:w-auto inline-flex items-center justify-center space-x-2 px-8 py-4 rounded-2xl bg-rose-600 hover:bg-rose-700 text-white font-bold text-base shadow-lg shadow-rose-600/30 hover:shadow-xl hover:shadow-rose-600/40 transition-all transform hover:-translate-y-0.5"
              >
                <AlertCircle className="w-5 h-5 text-rose-200" />
                <span>Post Emergency Request</span>
              </Link>

              <Link
                to="/find-donors"
                className="w-full sm:w-auto inline-flex items-center justify-center space-x-2 px-8 py-4 rounded-2xl bg-white dark:bg-slate-800 hover:bg-slate-50 dark:hover:bg-slate-700/80 text-slate-800 dark:text-white font-bold text-base border border-slate-200 dark:border-slate-700 shadow-sm transition-all"
              >
                <Search className="w-5 h-5 text-rose-500" />
                <span>Find Nearby Donors</span>
              </Link>
            </div>

            <div className="pt-4 flex items-center justify-center space-x-6 text-xs text-slate-500 dark:text-slate-400">
              <span className="flex items-center space-x-1">
                <ShieldCheck className="w-4 h-4 text-emerald-500" />
                <span>DPDP 2023 Consent Guard</span>
              </span>
              <span>•</span>
              <span>100% Voluntary & Non-Commercial</span>
              <span>•</span>
              <span>Indian Helplines 112 / 1910</span>
            </div>
          </div>
        </div>
      </section>

      {/* Impact Stats */}
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="grid grid-cols-2 lg:grid-cols-4 gap-4 sm:gap-6">
          {stats.map((stat, idx) => (
            <div
              key={idx}
              className="bg-white dark:bg-slate-800/80 rounded-2xl p-6 border border-slate-200 dark:border-slate-700/60 shadow-sm hover:shadow transition"
            >
              <div className="w-12 h-12 rounded-xl bg-rose-50 dark:bg-rose-950/60 border border-rose-100 dark:border-rose-900/40 flex items-center justify-center text-rose-600 dark:text-rose-400 mb-4">
                <stat.icon className="w-6 h-6" />
              </div>
              <div className="text-3xl font-black text-slate-900 dark:text-white">{stat.value}</div>
              <div className="text-sm font-bold text-slate-700 dark:text-slate-200 mt-1">{stat.label}</div>
              <div className="text-xs text-slate-500 dark:text-slate-400 mt-0.5">{stat.desc}</div>
            </div>
          ))}
        </div>
      </section>

      {/* How BloodBridge Works */}
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="text-center max-w-2xl mx-auto mb-12">
          <span className="text-xs font-bold uppercase tracking-wider text-rose-600 dark:text-rose-400">
            Simple 3-Step Process
          </span>
          <h2 className="text-3xl font-extrabold text-slate-900 dark:text-white mt-1">How BloodBridge Works</h2>
          <p className="text-sm text-slate-600 dark:text-slate-400 mt-2">
            Engineered for rapid response during critical hospital situations while keeping donor data completely safe.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div className="bg-white dark:bg-slate-800 rounded-3xl p-8 border border-slate-200 dark:border-slate-700 relative shadow-sm">
            <div className="w-10 h-10 rounded-2xl bg-rose-600 text-white font-black flex items-center justify-center mb-6 text-lg shadow-md shadow-rose-600/30">
              1
            </div>
            <h3 className="text-lg font-bold text-slate-900 dark:text-white mb-2">Create Emergency Request</h3>
            <p className="text-sm text-slate-600 dark:text-slate-400 leading-relaxed">
              Family members or hospitals post the required blood group, units, hospital address, and urgency level. A
              unique request ID is issued instantly.
            </p>
          </div>

          <div className="bg-white dark:bg-slate-800 rounded-3xl p-8 border border-slate-200 dark:border-slate-700 relative shadow-sm">
            <div className="w-10 h-10 rounded-2xl bg-rose-600 text-white font-black flex items-center justify-center mb-6 text-lg shadow-md shadow-rose-600/30">
              2
            </div>
            <h3 className="text-lg font-bold text-slate-900 dark:text-white mb-2">Smart Match & Privacy Alert</h3>
            <p className="text-sm text-slate-600 dark:text-slate-400 leading-relaxed">
              Our ABO/Rh compatibility engine identifies eligible nearby voluntary donors and dispatches priority SMS
              and in-app notifications without exposing personal phone numbers.
            </p>
          </div>

          <div className="bg-white dark:bg-slate-800 rounded-3xl p-8 border border-slate-200 dark:border-slate-700 relative shadow-sm">
            <div className="w-10 h-10 rounded-2xl bg-rose-600 text-white font-black flex items-center justify-center mb-6 text-lg shadow-md shadow-rose-600/30">
              3
            </div>
            <h3 className="text-lg font-bold text-slate-900 dark:text-white mb-2">Consent & Hospital Donation</h3>
            <p className="text-sm text-slate-600 dark:text-slate-400 leading-relaxed">
              When a donor accepts, mutual contact details are securely unlocked. The donor proceeds to the hospital
              blood bank where standard medical cross-matching is performed.
            </p>
          </div>
        </div>
      </section>

      {/* ABO / Rh Compatibility Quick Explorer */}
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="bg-gradient-to-r from-rose-900 to-slate-900 rounded-3xl p-8 sm:p-12 text-white shadow-xl">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-8 items-center">
            <div className="space-y-4">
              <span className="text-xs font-bold uppercase tracking-wider text-rose-300">
                Red Blood Cell Compatibility
              </span>
              <h2 className="text-2xl sm:text-3xl font-extrabold tracking-tight">
                Understand Donor-Recipient Blood Group Matching
              </h2>
              <p className="text-sm text-rose-100 leading-relaxed">
                Do you know that <span className="font-bold text-white">O Negative</span> is the Universal Red Cell
                Donor, while <span className="font-bold text-white">AB Positive</span> can receive red blood cells from
                all ABO/Rh groups? Explore our scientific 8x8 matrix guide.
              </p>
              <div className="pt-2">
                <Link
                  to="/compatibility-guide"
                  className="inline-flex items-center space-x-2 px-6 py-3 rounded-xl bg-white text-rose-950 font-bold text-sm shadow hover:bg-rose-50 transition"
                >
                  <span>Open Interactive Compatibility Guide</span>
                  <ArrowRight className="w-4 h-4" />
                </Link>
              </div>
            </div>

            <div className="grid grid-cols-4 gap-2.5 text-center font-mono text-xs">
              {['O-', 'O+', 'A-', 'A+', 'B-', 'B+', 'AB-', 'AB+'].map((group) => (
                <div
                  key={group}
                  className="p-3 bg-white/10 backdrop-blur-md rounded-xl border border-white/10 hover:bg-white/20 transition cursor-default"
                >
                  <div className="text-lg font-black text-rose-300">{group}</div>
                  <div className="text-[10px] text-slate-300 mt-1">
                    {group === 'O-' ? 'Universal Donor' : group === 'AB+' ? 'Universal Recipient' : 'Compatible'}
                  </div>
                </div>
              ))}
            </div>
          </div>
        </div>
      </section>

      {/* Voluntary Donor CTA Section */}
      <section className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="bg-slate-100 dark:bg-slate-800/50 rounded-3xl p-8 sm:p-12 border border-slate-200 dark:border-slate-700 flex flex-col md:flex-row items-center justify-between gap-8">
          <div className="space-y-3 max-w-xl">
            <span className="text-xs font-bold uppercase tracking-wider text-rose-600 dark:text-rose-400">
              Be a Lifesaver
            </span>
            <h2 className="text-2xl sm:text-3xl font-extrabold text-slate-900 dark:text-white">
              Register as a Voluntary Donor
            </h2>
            <p className="text-sm text-slate-600 dark:text-slate-300 leading-relaxed">
              Your single donation can save up to three lives. Set your availability, choose notification preferences,
              and receive alerts only when someone in your vicinity truly needs your blood type.
            </p>
          </div>
          <Link
            to="/register-donor"
            className="flex-shrink-0 inline-flex items-center space-x-2 px-8 py-4 rounded-2xl bg-rose-600 hover:bg-rose-700 text-white font-bold text-sm shadow-md transition"
          >
            <Heart className="w-5 h-5 fill-white" />
            <span>Join BloodBridge Donors</span>
          </Link>
        </div>
      </section>

      {/* Frequently Asked Questions */}
      <section className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="text-center mb-8">
          <span className="text-xs font-bold uppercase tracking-wider text-rose-600 dark:text-rose-400">
            Clarifications & Rules
          </span>
          <h2 className="text-2xl sm:text-3xl font-extrabold text-slate-900 dark:text-white mt-1">
            Frequently Asked Questions
          </h2>
        </div>

        <div className="space-y-3">
          {faqs.map((faq, idx) => (
            <div
              key={idx}
              className="bg-white dark:bg-slate-800 rounded-2xl border border-slate-200 dark:border-slate-700/80 overflow-hidden shadow-sm transition"
            >
              <button
                onClick={() => setActiveFaq(activeFaq === idx ? null : idx)}
                className="w-full flex items-center justify-between p-5 text-left text-sm sm:text-base font-bold text-slate-900 dark:text-white focus:outline-none"
              >
                <span>{faq.q}</span>
                {activeFaq === idx ? (
                  <ChevronUp className="w-5 h-5 text-rose-500 flex-shrink-0" />
                ) : (
                  <ChevronDown className="w-5 h-5 text-slate-400 flex-shrink-0" />
                )}
              </button>
              {activeFaq === idx && (
                <div className="px-5 pb-5 text-xs sm:text-sm text-slate-600 dark:text-slate-300 leading-relaxed border-t border-slate-100 dark:border-slate-700/40 pt-3">
                  {faq.a}
                </div>
              )}
            </div>
          ))}
        </div>
      </section>
    </div>
  );
};
