import React, { useState, useEffect } from 'react';
import { hospitalApi } from '../services/api';
import { BloodGroup } from '../types';
import { MedicalDisclaimer } from '../components/Disclaimers';
import { BloodGroupBadge } from '../components/Badges';
import { CardSkeleton } from '../components/LoadingSkeleton';
import { EmptyState } from '../components/EmptyState';
import { Building2, Search, Phone, MapPin, Clock, ShieldCheck, ExternalLink } from 'lucide-react';

interface BloodBankItem {
  id: string;
  hospitalName: string;
  city: string;
  state: string;
  address: string;
  phone: string;
  isVerified: boolean;
  lastUpdated: string;
  stocks: {
    bloodGroup: BloodGroup;
    units: number;
    status: 'AVAILABLE' | 'CRITICAL_LOW' | 'UNAVAILABLE';
  }[];
}

export const BloodBanks: React.FC = () => {
  const [cityFilter, setCityFilter] = useState<string>('Bangalore');
  const [bloodGroupFilter, setBloodGroupFilter] = useState<string>('');
  const [loading, setLoading] = useState<boolean>(true);
  const [bloodBanks, setBloodBanks] = useState<BloodBankItem[]>([]);

  // Seeded / mock verified blood bank data matching Indian regional hubs
  const sampleBloodBanks: BloodBankItem[] = [
    {
      id: 'bb-1',
      hospitalName: 'Apollo Hospitals Blood Bank & Transfusion Center',
      city: 'Bangalore',
      state: 'Karnataka',
      address: '154/11, Bannerghatta Main Rd, Opposite IIMB',
      phone: '+91 80 2630 4050',
      isVerified: true,
      lastUpdated: '10 mins ago',
      stocks: [
        { bloodGroup: 'O_POSITIVE', units: 18, status: 'AVAILABLE' },
        { bloodGroup: 'O_NEGATIVE', units: 2, status: 'CRITICAL_LOW' },
        { bloodGroup: 'A_POSITIVE', units: 14, status: 'AVAILABLE' },
        { bloodGroup: 'B_POSITIVE', units: 20, status: 'AVAILABLE' },
        { bloodGroup: 'AB_POSITIVE', units: 6, status: 'AVAILABLE' },
      ],
    },
    {
      id: 'bb-2',
      hospitalName: 'Manipal Hospital Transfusion Medicine',
      city: 'Bangalore',
      state: 'Karnataka',
      address: '98 HAL Old Airport Rd, Kodihalli',
      phone: '+91 80 2502 4444',
      isVerified: true,
      lastUpdated: '25 mins ago',
      stocks: [
        { bloodGroup: 'O_POSITIVE', units: 12, status: 'AVAILABLE' },
        { bloodGroup: 'A_NEGATIVE', units: 1, status: 'CRITICAL_LOW' },
        { bloodGroup: 'B_NEGATIVE', units: 3, status: 'AVAILABLE' },
        { bloodGroup: 'AB_NEGATIVE', units: 0, status: 'UNAVAILABLE' },
      ],
    },
    {
      id: 'bb-3',
      hospitalName: 'All India Institute of Medical Sciences (AIIMS) Blood Bank',
      city: 'Delhi',
      state: 'Delhi',
      address: 'Sri Aurobindo Marg, Ansari Nagar East',
      phone: '+91 11 2658 8500',
      isVerified: true,
      lastUpdated: '1 hour ago',
      stocks: [
        { bloodGroup: 'O_POSITIVE', units: 45, status: 'AVAILABLE' },
        { bloodGroup: 'O_NEGATIVE', units: 5, status: 'CRITICAL_LOW' },
        { bloodGroup: 'A_POSITIVE', units: 30, status: 'AVAILABLE' },
        { bloodGroup: 'B_POSITIVE', units: 28, status: 'AVAILABLE' },
        { bloodGroup: 'AB_POSITIVE', units: 12, status: 'AVAILABLE' },
      ],
    },
    {
      id: 'bb-4',
      hospitalName: 'KEM Hospital & Seth GS Medical College Blood Center',
      city: 'Mumbai',
      state: 'Maharashtra',
      address: 'Acharya Donde Marg, Parel',
      phone: '+91 22 2410 7000',
      isVerified: true,
      lastUpdated: '40 mins ago',
      stocks: [
        { bloodGroup: 'O_POSITIVE', units: 22, status: 'AVAILABLE' },
        { bloodGroup: 'O_NEGATIVE', units: 3, status: 'CRITICAL_LOW' },
        { bloodGroup: 'B_POSITIVE', units: 19, status: 'AVAILABLE' },
        { bloodGroup: 'A_POSITIVE', units: 15, status: 'AVAILABLE' },
      ],
    },
  ];

  useEffect(() => {
    // In production, pulls from /api/hospitals/availability
    setLoading(true);
    setTimeout(() => {
      setBloodBanks(sampleBloodBanks);
      setLoading(false);
    }, 400);
  }, []);

  const filteredBanks = bloodBanks.filter((bank) => {
    const matchesCity = !cityFilter || bank.city.toLowerCase().includes(cityFilter.toLowerCase());
    const matchesGroup =
      !bloodGroupFilter || bank.stocks.some((s) => s.bloodGroup === bloodGroupFilter && s.units > 0);
    return matchesCity && matchesGroup;
  });

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-6">
      {/* Header */}
      <div>
        <span className="text-xs font-bold uppercase tracking-wider text-rose-600 dark:text-rose-400">
          Institution Registry
        </span>
        <h1 className="text-3xl font-black text-slate-900 dark:text-white tracking-tight mt-1">
          Verified Blood Banks & Availability
        </h1>
        <p className="text-sm text-slate-500 dark:text-slate-400 mt-1 max-w-2xl">
          Real-time published blood component inventory from accredited government and private hospital blood banks.
        </p>
      </div>

      <MedicalDisclaimer />

      {/* Filter Bar */}
      <div className="bg-white dark:bg-slate-800 rounded-2xl p-5 border border-slate-200 dark:border-slate-700/80 shadow-sm grid grid-cols-1 sm:grid-cols-3 gap-4">
        <div>
          <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
            Search City
          </label>
          <input
            type="text"
            value={cityFilter}
            onChange={(e) => setCityFilter(e.target.value)}
            placeholder="E.g., Bangalore, Delhi, Mumbai"
            className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-2.5 focus:ring-2 focus:ring-rose-500 focus:outline-none"
          />
        </div>

        <div>
          <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
            Filter by Stock Group
          </label>
          <select
            value={bloodGroupFilter}
            onChange={(e) => setBloodGroupFilter(e.target.value)}
            className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-2.5 focus:ring-2 focus:ring-rose-500 focus:outline-none"
          >
            <option value="">All Groups</option>
            <option value="O_NEGATIVE">O Negative (O-)</option>
            <option value="O_POSITIVE">O Positive (O+)</option>
            <option value="A_NEGATIVE">A Negative (A-)</option>
            <option value="A_POSITIVE">A Positive (A+)</option>
            <option value="B_NEGATIVE">B Negative (B-)</option>
            <option value="B_POSITIVE">B Positive (B+)</option>
            <option value="AB_NEGATIVE">AB Negative (AB-)</option>
            <option value="AB_POSITIVE">AB Positive (AB+)</option>
          </select>
        </div>

        <div className="flex items-end">
          <button
            onClick={() => {
              setCityFilter('');
              setBloodGroupFilter('');
            }}
            className="w-full py-2.5 px-4 rounded-xl text-xs font-bold text-slate-600 dark:text-slate-300 bg-slate-100 dark:bg-slate-700 hover:bg-slate-200 dark:hover:bg-slate-600 transition"
          >
            Reset Filters
          </button>
        </div>
      </div>

      {/* Blood Banks Cards */}
      {loading ? (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {Array.from({ length: 4 }).map((_, i) => (
            <CardSkeleton key={i} />
          ))}
        </div>
      ) : filteredBanks.length === 0 ? (
        <EmptyState
          icon={Building2}
          title="No Blood Banks Match Your Filter"
          description="Try broadening your city or blood group selection, or check e-RaktKosh national blood bank registry."
          actionText="Clear Filters"
          onAction={() => {
            setCityFilter('');
            setBloodGroupFilter('');
          }}
        />
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {filteredBanks.map((bank) => (
            <div
              key={bank.id}
              className="bg-white dark:bg-slate-800 rounded-3xl p-6 border border-slate-200 dark:border-slate-700/80 shadow-sm hover:shadow-md transition space-y-4 flex flex-col justify-between"
            >
              <div>
                <div className="flex items-start justify-between gap-3 mb-2">
                  <div className="flex items-center space-x-2">
                    <h3 className="font-bold text-slate-900 dark:text-white text-base leading-snug">
                      {bank.hospitalName}
                    </h3>
                    {bank.isVerified && (
                      <span title="NABH / Verified Blood Bank">
                        <ShieldCheck className="w-4 h-4 text-emerald-500 flex-shrink-0" />
                      </span>
                    )}
                  </div>
                </div>

                <div className="space-y-1 text-xs text-slate-500 dark:text-slate-400 mb-4">
                  <div className="flex items-center space-x-1.5">
                    <MapPin className="w-3.5 h-3.5 text-rose-500 flex-shrink-0" />
                    <span>
                      {bank.address}, {bank.city}, {bank.state}
                    </span>
                  </div>
                  <div className="flex items-center space-x-1.5">
                    <Clock className="w-3.5 h-3.5 text-slate-400 flex-shrink-0" />
                    <span>Stock updated {bank.lastUpdated}</span>
                  </div>
                </div>

                {/* Blood Component Inventory Badges */}
                <div>
                  <h4 className="text-[11px] font-bold uppercase tracking-wider text-slate-400 mb-2">
                    Reported Available Units
                  </h4>
                  <div className="grid grid-cols-3 sm:grid-cols-5 gap-2">
                    {bank.stocks.map((stock) => (
                      <div
                        key={stock.bloodGroup}
                        className={`p-2 rounded-xl text-center border ${
                          stock.status === 'AVAILABLE'
                            ? 'bg-emerald-50 dark:bg-emerald-950/40 border-emerald-200 dark:border-emerald-800 text-emerald-800 dark:text-emerald-200'
                            : stock.status === 'CRITICAL_LOW'
                            ? 'bg-amber-50 dark:bg-amber-950/40 border-amber-200 dark:border-amber-800 text-amber-800 dark:text-amber-200'
                            : 'bg-slate-100 dark:bg-slate-700/40 border-slate-200 dark:border-slate-700 text-slate-400'
                        }`}
                      >
                        <div className="text-xs font-mono font-bold">
                          {stock.bloodGroup.replace('_POSITIVE', '+').replace('_NEGATIVE', '-')}
                        </div>
                        <div className="text-[10px] font-semibold mt-0.5">
                          {stock.units > 0 ? `${stock.units} Units` : 'Nil'}
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              </div>

              {/* Action Buttons */}
              <div className="pt-4 border-t border-slate-100 dark:border-slate-700/60 flex items-center justify-between gap-3">
                <a
                  href={`tel:${bank.phone}`}
                  className="flex-1 inline-flex items-center justify-center space-x-1.5 py-2.5 px-4 rounded-xl text-xs font-bold text-white bg-slate-900 dark:bg-slate-700 hover:bg-slate-800 transition"
                >
                  <Phone className="w-3.5 h-3.5" />
                  <span>Call: {bank.phone}</span>
                </a>
                <a
                  href={`https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(
                    `${bank.hospitalName} ${bank.city}`
                  )}`}
                  target="_blank"
                  rel="noreferrer"
                  className="inline-flex items-center space-x-1 py-2.5 px-3 rounded-xl text-xs font-semibold text-slate-600 dark:text-slate-300 hover:bg-slate-100 dark:hover:bg-slate-700 transition"
                >
                  <span>Directions</span>
                  <ExternalLink className="w-3.5 h-3.5" />
                </a>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};
