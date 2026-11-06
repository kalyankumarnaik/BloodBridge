import React from 'react';
import { DonorSearchResult } from '../types';
import { BloodGroupBadge } from './Badges';
import { MapPin, CheckCircle2, Phone, Shield, Clock } from 'lucide-react';

interface DonorCardProps {
  donor: DonorSearchResult;
  onRequestContact: (donor: DonorSearchResult) => void;
}

export const DonorCard: React.FC<DonorCardProps> = ({ donor, onRequestContact }) => {
  const isAvailable = donor.availability === 'AVAILABLE_NOW';
  const isOnCall = donor.availability === 'ON_CALL';

  return (
    <div className="bg-white dark:bg-slate-800 rounded-2xl border border-slate-200 dark:border-slate-700/60 p-5 shadow-sm hover:shadow-md transition-all flex flex-col justify-between">
      <div>
        {/* Header: Blood Group, Availability & Verified Status */}
        <div className="flex items-start justify-between gap-3 mb-4">
          <div className="flex items-center space-x-3">
            <BloodGroupBadge bloodGroup={donor.bloodGroup} size="lg" />
            <div>
              <div className="flex items-center space-x-1.5">
                <h3 className="font-bold text-slate-900 dark:text-white text-base">
                  {donor.displayName}
                </h3>
                {donor.isVerified && (
                  <span title="Verified Donor Profile">
                    <CheckCircle2 className="w-4 h-4 text-emerald-500 fill-emerald-50 dark:fill-emerald-950" />
                  </span>
                )}
              </div>
              <p className="text-xs text-slate-500 dark:text-slate-400 font-mono">
                ID: {donor.donorUuid.slice(0, 8)}...
              </p>
            </div>
          </div>

          <div>
            {isAvailable ? (
              <span className="inline-flex items-center px-2 py-0.5 rounded-full text-xs font-semibold bg-emerald-100 dark:bg-emerald-950/80 text-emerald-700 dark:text-emerald-300 border border-emerald-300 dark:border-emerald-800">
                <span className="w-1.5 h-1.5 rounded-full bg-emerald-500 mr-1.5 animate-pulse" />
                Available Now
              </span>
            ) : isOnCall ? (
              <span className="inline-flex items-center px-2 py-0.5 rounded-full text-xs font-medium bg-amber-100 dark:bg-amber-950/80 text-amber-700 dark:text-amber-300 border border-amber-300 dark:border-amber-800">
                <Clock className="w-3 h-3 mr-1" />
                On Call
              </span>
            ) : (
              <span className="inline-flex items-center px-2 py-0.5 rounded-full text-xs font-medium bg-slate-100 dark:bg-slate-700 text-slate-600 dark:text-slate-400">
                Temporarily Inactive
              </span>
            )}
          </div>
        </div>

        {/* Location & Distance Info */}
        <div className="space-y-2 text-xs text-slate-600 dark:text-slate-300 mb-4">
          <div className="flex items-center space-x-1.5">
            <MapPin className="w-3.5 h-3.5 text-rose-500 flex-shrink-0" />
            <span>
              {donor.city}, {donor.state} <span className="text-slate-400 font-mono">({donor.pinCodePrefix})</span>
            </span>
          </div>

          <div className="flex items-center justify-between text-xs pt-1">
            <span className="text-slate-500 dark:text-slate-400">Approx. Distance:</span>
            <span className="font-semibold text-slate-800 dark:text-slate-200 bg-slate-100 dark:bg-slate-700/60 px-2 py-0.5 rounded">
              {donor.distanceBand}
            </span>
          </div>

          <div className="flex items-center justify-between text-xs">
            <span className="text-slate-500 dark:text-slate-400">Past Donations:</span>
            <span className="font-medium text-slate-700 dark:text-slate-300">
              {donor.totalDonations} {donor.totalDonations === 1 ? 'time' : 'times'}
            </span>
          </div>
        </div>
      </div>

      {/* Action / Privacy Area */}
      <div className="pt-3 border-t border-slate-100 dark:border-slate-700/60 space-y-2">
        {donor.hasActiveConsent && donor.contactPhone ? (
          <a
            href={`tel:${donor.contactPhone}`}
            className="w-full flex items-center justify-center space-x-2 py-2 px-4 rounded-xl text-xs font-bold text-white bg-emerald-600 hover:bg-emerald-700 transition shadow-sm"
          >
            <Phone className="w-3.5 h-3.5" />
            <span>Call Donor: {donor.contactPhone}</span>
          </a>
        ) : (
          <button
            onClick={() => onRequestContact(donor)}
            className="w-full flex items-center justify-center space-x-1.5 py-2 px-4 rounded-xl text-xs font-bold text-white bg-rose-600 hover:bg-rose-700 dark:bg-rose-500 dark:hover:bg-rose-600 transition shadow-sm"
          >
            <Shield className="w-3.5 h-3.5" />
            <span>Request Contact Details</span>
          </button>
        )}

        <p className="text-[10px] text-center text-slate-400 dark:text-slate-500">
          Phone & exact location masked per DPDP Act until donor consents
        </p>
      </div>
    </div>
  );
};
