import React, { useState, useEffect } from 'react';
import { DonorSearchResult, BloodGroup, DonorAvailability } from '../types';
import { donorApi } from '../services/api';
import { DonorCard } from '../components/DonorCard';
import { ContactModal } from '../components/ContactModal';
import { MapViewer } from '../components/MapViewer';
import { MedicalDisclaimer, PrivacyDisclaimer } from '../components/Disclaimers';
import { CardSkeleton } from '../components/LoadingSkeleton';
import { EmptyState } from '../components/EmptyState';
import { Search, Filter, Map, LayoutGrid, RotateCcw, AlertTriangle, Users } from 'lucide-react';

export const FindDonors: React.FC = () => {
  const [bloodGroup, setBloodGroup] = useState<string>('');
  const [city, setCity] = useState<string>('Bangalore');
  const [pinCode, setPinCode] = useState<string>('');
  const [radiusKm, setRadiusKm] = useState<number>(25);
  const [availability, setAvailability] = useState<string>('');
  const [verifiedOnly, setVerifiedOnly] = useState<boolean>(false);
  const [viewMode, setViewMode] = useState<'grid' | 'map'>('grid');

  const [donors, setDonors] = useState<DonorSearchResult[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const [selectedDonorForContact, setSelectedDonorForContact] = useState<DonorSearchResult | null>(null);

  const bloodGroupOptions: { label: string; value: BloodGroup | '' }[] = [
    { label: 'All Blood Groups', value: '' },
    { label: 'O Negative (Universal RBC Donor)', value: 'O_NEGATIVE' },
    { label: 'O Positive', value: 'O_POSITIVE' },
    { label: 'A Negative', value: 'A_NEGATIVE' },
    { label: 'A Positive', value: 'A_POSITIVE' },
    { label: 'B Negative', value: 'B_NEGATIVE' },
    { label: 'B Positive', value: 'B_POSITIVE' },
    { label: 'AB Negative', value: 'AB_NEGATIVE' },
    { label: 'AB Positive (Universal RBC Recipient)', value: 'AB_POSITIVE' },
  ];

  const fetchDonors = async () => {
    setLoading(true);
    setError(null);
    try {
      const results = await donorApi.searchDonors({
        bloodGroup: (bloodGroup as BloodGroup) || undefined,
        city: city.trim() || undefined,
        pinCode: pinCode.trim() || undefined,
        radiusKm,
        availability: (availability as DonorAvailability) || undefined,
        verifiedOnly: verifiedOnly || undefined,
      });
      setDonors(results);
    } catch (err: any) {
      console.error('Error fetching donors:', err);
      setError('Unable to load donors. Please check connection or search parameters.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchDonors();
  }, []);

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    fetchDonors();
  };

  const handleReset = () => {
    setBloodGroup('');
    setCity('');
    setPinCode('');
    setRadiusKm(25);
    setAvailability('');
    setVerifiedOnly(false);
    setTimeout(fetchDonors, 50);
  };

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-6">
      {/* Disclaimers */}
      <MedicalDisclaimer />
      <PrivacyDisclaimer />

      {/* Page Header */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <h1 className="text-2xl sm:text-3xl font-black text-slate-900 dark:text-white tracking-tight">
            Find Nearby Voluntary Donors
          </h1>
          <p className="text-sm text-slate-500 dark:text-slate-400 mt-1">
            Search consenting voluntary donors filtered by red blood cell compatibility, location radius, and availability.
          </p>
        </div>

        {/* View Switcher */}
        <div className="flex items-center space-x-2 bg-slate-100 dark:bg-slate-800 p-1 rounded-xl self-start">
          <button
            onClick={() => setViewMode('grid')}
            className={`inline-flex items-center space-x-1.5 px-3 py-1.5 rounded-lg text-xs font-semibold transition ${
              viewMode === 'grid'
                ? 'bg-white dark:bg-slate-700 text-rose-600 dark:text-white shadow-sm'
                : 'text-slate-600 dark:text-slate-400 hover:text-slate-900 dark:hover:text-white'
            }`}
          >
            <LayoutGrid className="w-4 h-4" />
            <span>List & Grid</span>
          </button>
          <button
            onClick={() => setViewMode('map')}
            className={`inline-flex items-center space-x-1.5 px-3 py-1.5 rounded-lg text-xs font-semibold transition ${
              viewMode === 'map'
                ? 'bg-white dark:bg-slate-700 text-rose-600 dark:text-white shadow-sm'
                : 'text-slate-600 dark:text-slate-400 hover:text-slate-900 dark:hover:text-white'
            }`}
          >
            <Map className="w-4 h-4" />
            <span>Area Map</span>
          </button>
        </div>
      </div>

      {/* Filter Card */}
      <form
        onSubmit={handleSearch}
        className="bg-white dark:bg-slate-800 rounded-2xl p-5 border border-slate-200 dark:border-slate-700/80 shadow-sm space-y-4"
      >
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          {/* Blood Group */}
          <div>
            <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
              Blood Group
            </label>
            <select
              value={bloodGroup}
              onChange={(e) => setBloodGroup(e.target.value)}
              className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-2.5 focus:ring-2 focus:ring-rose-500 focus:outline-none"
            >
              {bloodGroupOptions.map((opt) => (
                <option key={opt.label} value={opt.value}>
                  {opt.label}
                </option>
              ))}
            </select>
          </div>

          {/* City */}
          <div>
            <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
              City / District
            </label>
            <input
              type="text"
              value={city}
              onChange={(e) => setCity(e.target.value)}
              placeholder="E.g., Bangalore, Mumbai"
              className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-2.5 focus:ring-2 focus:ring-rose-500 focus:outline-none"
            />
          </div>

          {/* PIN Code */}
          <div>
            <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
              PIN Code (India)
            </label>
            <input
              type="text"
              maxLength={6}
              value={pinCode}
              onChange={(e) => setPinCode(e.target.value)}
              placeholder="E.g., 560001"
              className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-2.5 focus:ring-2 focus:ring-rose-500 focus:outline-none"
            />
          </div>

          {/* Availability */}
          <div>
            <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
              Status
            </label>
            <select
              value={availability}
              onChange={(e) => setAvailability(e.target.value)}
              className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-2.5 focus:ring-2 focus:ring-rose-500 focus:outline-none"
            >
              <option value="">All Availabilities</option>
              <option value="AVAILABLE_NOW">Available Now</option>
              <option value="ON_CALL">On Call Only</option>
            </select>
          </div>
        </div>

        {/* Distance Slider & Verified Checkbox */}
        <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-4 pt-2 border-t border-slate-100 dark:border-slate-700/60">
          <div className="flex items-center space-x-3 w-full sm:w-1/2">
            <span className="text-xs font-bold uppercase tracking-wide text-slate-600 dark:text-slate-400 whitespace-nowrap">
              Radius: {radiusKm} km
            </span>
            <input
              type="range"
              min={5}
              max={100}
              step={5}
              value={radiusKm}
              onChange={(e) => setRadiusKm(Number(e.target.value))}
              className="w-full accent-rose-600 h-2 bg-slate-200 dark:bg-slate-700 rounded-lg cursor-pointer"
            />
          </div>

          <div className="flex items-center space-x-4">
            <label className="inline-flex items-center space-x-2 text-xs font-medium text-slate-700 dark:text-slate-300 cursor-pointer">
              <input
                type="checkbox"
                checked={verifiedOnly}
                onChange={(e) => setVerifiedOnly(e.target.checked)}
                className="rounded text-rose-600 focus:ring-rose-500"
              />
              <span>Verified Donors Only</span>
            </label>

            <button
              type="button"
              onClick={handleReset}
              className="inline-flex items-center space-x-1 px-3 py-1.5 rounded-xl text-xs font-medium text-slate-500 hover:text-slate-700 dark:text-slate-400 dark:hover:text-white"
            >
              <RotateCcw className="w-3.5 h-3.5" />
              <span>Reset</span>
            </button>

            <button
              type="submit"
              className="inline-flex items-center space-x-1.5 px-5 py-2 rounded-xl text-xs font-bold text-white bg-rose-600 hover:bg-rose-700 shadow transition"
            >
              <Search className="w-3.5 h-3.5" />
              <span>Search</span>
            </button>
          </div>
        </div>
      </form>

      {/* Results Header */}
      <div className="flex items-center justify-between text-xs text-slate-500 dark:text-slate-400 px-1">
        <span>
          Showing <strong className="text-slate-900 dark:text-white">{donors.length}</strong> voluntary donors within{' '}
          {radiusKm} km
        </span>
        <span>Distance calculations based on Haversine coordinates</span>
      </div>

      {/* Content Area */}
      {loading ? (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5">
          {Array.from({ length: 6 }).map((_, i) => (
            <CardSkeleton key={i} />
          ))}
        </div>
      ) : error ? (
        <div className="p-6 bg-rose-50 dark:bg-rose-950/60 border border-rose-200 dark:border-rose-800 rounded-2xl text-center text-rose-700 dark:text-rose-300 text-sm">
          {error}
        </div>
      ) : donors.length === 0 ? (
        <EmptyState
          icon={Users}
          title="No voluntary donors found"
          description="Try increasing the search radius, clearing specific blood filters, or post an Emergency Blood Request to notify the community."
          actionText="Post Emergency Request"
          onAction={() => (window.location.href = '/emergency-request')}
        />
      ) : viewMode === 'grid' ? (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5">
          {donors.map((donor) => (
            <DonorCard
              key={donor.donorUuid}
              donor={donor}
              onRequestContact={(d) => setSelectedDonorForContact(d)}
            />
          ))}
        </div>
      ) : (
        <div className="space-y-4">
          <MapViewer donors={donors} onSelectDonor={(d) => setSelectedDonorForContact(d)} height="550px" />
          <p className="text-xs text-slate-500 text-center">
            Green circles indicate 1.5 km privacy radius approximations. Donor home addresses are protected.
          </p>
        </div>
      )}

      {/* Contact Modal */}
      {selectedDonorForContact && (
        <ContactModal
          donor={selectedDonorForContact}
          onClose={() => setSelectedDonorForContact(null)}
          onSuccess={() => {
            fetchDonors();
          }}
        />
      )}
    </div>
  );
};
