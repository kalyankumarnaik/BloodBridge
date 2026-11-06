import React from 'react';
import { RequestPriority, BloodGroup, RequestStatus } from '../types';

interface UrgencyBadgeProps {
  priority: RequestPriority;
  className?: string;
}

export const UrgencyBadge: React.FC<UrgencyBadgeProps> = ({ priority, className = '' }) => {
  switch (priority) {
    case 'CRITICAL':
      return (
        <span
          className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-bold uppercase tracking-wide bg-rose-100 dark:bg-rose-950/80 text-rose-700 dark:text-rose-300 border border-rose-300 dark:border-rose-800 animate-pulse ${className}`}
        >
          <span className="w-1.5 h-1.5 mr-1.5 rounded-full bg-rose-600 animate-ping" />
          Critical
        </span>
      );
    case 'HIGH':
    case 'URGENT':
      return (
        <span
          className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-semibold uppercase tracking-wide bg-amber-100 dark:bg-amber-950/80 text-amber-700 dark:text-amber-300 border border-amber-300 dark:border-amber-800 ${className}`}
        >
          <span className="w-1.5 h-1.5 mr-1.5 rounded-full bg-amber-500" />
          High Urgency
        </span>
      );
    case 'NORMAL':
    case 'PLANNED':
    default:
      return (
        <span
          className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium uppercase tracking-wide bg-blue-100 dark:bg-blue-950/80 text-blue-700 dark:text-blue-300 border border-blue-300 dark:border-blue-800 ${className}`}
        >
          <span className="w-1.5 h-1.5 mr-1.5 rounded-full bg-blue-500" />
          Normal
        </span>
      );
  }
};

interface BloodGroupBadgeProps {
  bloodGroup: BloodGroup | string;
  size?: 'sm' | 'md' | 'lg';
  className?: string;
}

export const BloodGroupBadge: React.FC<BloodGroupBadgeProps> = ({ bloodGroup, size = 'md', className = '' }) => {
  const sizeClasses = {
    sm: 'text-xs px-2 py-0.5 font-bold',
    md: 'text-sm px-2.5 py-1 font-extrabold',
    lg: 'text-lg px-3.5 py-1.5 font-black',
  };

  const isNegative = bloodGroup.includes('NEGATIVE') || bloodGroup.includes('-');

  return (
    <span
      className={`inline-flex items-center justify-center rounded-lg shadow-sm font-mono ${
        isNegative
          ? 'bg-rose-700 text-white border border-rose-800 dark:bg-rose-600'
          : 'bg-rose-600 text-white border border-rose-700 dark:bg-rose-500'
      } ${sizeClasses[size]} ${className}`}
    >
      {bloodGroup.replace('_POSITIVE', '+').replace('_NEGATIVE', '-')}
    </span>
  );
};

interface StatusBadgeProps {
  status: RequestStatus;
  className?: string;
}

export const StatusBadge: React.FC<StatusBadgeProps> = ({ status, className = '' }) => {
  const styles: Record<RequestStatus, string> = {
    PENDING: 'bg-amber-100 text-amber-800 dark:bg-amber-900/40 dark:text-amber-300 border-amber-200 dark:border-amber-700',
    VERIFIED: 'bg-blue-100 text-blue-800 dark:bg-blue-900/40 dark:text-blue-300 border-blue-200 dark:border-blue-700',
    ACTIVE: 'bg-emerald-100 text-emerald-800 dark:bg-emerald-900/40 dark:text-emerald-300 border-emerald-200 dark:border-emerald-700',
    DONOR_RESPONDED: 'bg-purple-100 text-purple-800 dark:bg-purple-900/40 dark:text-purple-300 border-purple-200 dark:border-purple-700',
    FULFILLED: 'bg-teal-100 text-teal-800 dark:bg-teal-900/40 dark:text-teal-300 border-teal-200 dark:border-teal-700',
    CANCELLED: 'bg-slate-100 text-slate-800 dark:bg-slate-800 dark:text-slate-400 border-slate-300 dark:border-slate-700',
    EXPIRED: 'bg-rose-100 text-rose-800 dark:bg-rose-900/40 dark:text-rose-400 border-rose-200 dark:border-rose-700',
  };

  return (
    <span
      className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium border ${styles[status] || styles.PENDING} ${className}`}
    >
      {status.replace('_', ' ')}
    </span>
  );
};
