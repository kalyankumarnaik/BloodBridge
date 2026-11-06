import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { requestApi } from '../services/api';
import { BloodGroup, RequestPriority, ContactPreference, BloodRequest } from '../types';
import { MedicalDisclaimer } from '../components/Disclaimers';
import { AlertCircle, CheckCircle2, Share2, Copy, ArrowRight, Hospital, Calendar, Phone } from 'lucide-react';

export const EmergencyRequest: React.FC = () => {
  const navigate = useNavigate();

  const [patientName, setPatientName] = useState('');
  const [bloodGroup, setBloodGroup] = useState<BloodGroup>('O_POSITIVE');
  const [unitsRequired, setUnitsRequired] = useState<number>(2);
  const [priority, setPriority] = useState<RequestPriority>('CRITICAL');
  const [hospitalName, setHospitalName] = useState('');
  const [hospitalAddress, setHospitalAddress] = useState('');
  const [city, setCity] = useState('Bangalore');
  const [state, setState] = useState('Karnataka');
  const [pinCode, setPinCode] = useState('');
  const [requiredBy, setRequiredBy] = useState(() => {
    const d = new Date();
    d.setHours(d.getHours() + 12);
    return d.toISOString().slice(0, 16);
  });
  const [contactPreference, setContactPreference] = useState<ContactPreference>('PHONE');
  const [additionalNotes, setAdditionalNotes] = useState('');
  const [consentAcknowledged, setConsentAcknowledged] = useState(false);

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [createdRequest, setCreatedRequest] = useState<BloodRequest | null>(null);
  const [copiedLink, setCopiedLink] = useState(false);

  const indianStates = [
    'Andhra Pradesh', 'Arunachal Pradesh', 'Assam', 'Bihar', 'Chhattisgarh', 'Goa', 'Gujarat',
    'Haryana', 'Himachal Pradesh', 'Jharkhand', 'Karnataka', 'Kerala', 'Madhya Pradesh',
    'Maharashtra', 'Manipur', 'Meghalaya', 'Mizoram', 'Nagaland', 'Odisha', 'Punjab',
    'Rajasthan', 'Sikkim', 'Tamil Nadu', 'Telangana', 'Tripura', 'Uttar Pradesh', 'Uttarakhand',
    'West Bengal', 'Delhi', 'Jammu and Kashmir', 'Ladakh', 'Puducherry'
  ];

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!consentAcknowledged) {
      setError('Please acknowledge the medical disclaimer and verify emergency terms.');
      return;
    }
    if (!/^\d{6}$/.test(pinCode.trim())) {
      setError('Please enter a valid 6-digit Indian PIN Code.');
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const response = await requestApi.createRequest({
        patientName: patientName.trim(),
        bloodGroup,
        unitsRequired,
        priority,
        hospitalName: hospitalName.trim(),
        hospitalAddress: hospitalAddress.trim(),
        city: city.trim(),
        state: state.trim(),
        pinCode: pinCode.trim(),
        requiredBy: new Date(requiredBy).toISOString(),
        contactPreference,
        additionalNotes: additionalNotes.trim(),
      });
      setCreatedRequest(response);
    } catch (err: any) {
      console.error('Request creation error:', err);
      setError(
        err.response?.data?.message ||
          'Failed to post emergency request. Please ensure you are logged in or verify your network.'
      );
    } finally {
      setLoading(false);
    }
  };

  const getWhatsAppShareUrl = () => {
    if (!createdRequest) return '';
    const text = `🚨 *URGENT BLOOD REQUIREMENT* 🚨\n\nPatient: ${createdRequest.patientName}\nBlood Group: *${createdRequest.bloodGroup}*\nUnits Needed: ${createdRequest.unitsRequired}\nHospital: ${createdRequest.hospitalName}, ${createdRequest.city} (${createdRequest.pinCode})\nPriority: *${createdRequest.priority}*\nRequest ID: *${createdRequest.requestId}*\n\nPlease respond or share to help save a life via BloodBridge:\n${window.location.origin}/find-donors?requestId=${createdRequest.requestId}`;
    return `https://wa.me/?text=${encodeURIComponent(text)}`;
  };

  const handleCopyShare = () => {
    if (!createdRequest) return;
    const url = `${window.location.origin}/emergency-request?id=${createdRequest.requestId}`;
    navigator.clipboard.writeText(url);
    setCopiedLink(true);
    setTimeout(() => setCopiedLink(false), 3000);
  };

  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8 space-y-6">
      <MedicalDisclaimer />

      {createdRequest ? (
        /* Success Screen */
        <div className="bg-white dark:bg-slate-800 rounded-3xl p-8 sm:p-10 border border-slate-200 dark:border-slate-700 shadow-xl space-y-6">
          <div className="text-center space-y-3">
            <div className="w-16 h-16 bg-emerald-100 dark:bg-emerald-950/80 text-emerald-600 dark:text-emerald-400 rounded-2xl flex items-center justify-center mx-auto shadow-sm">
              <CheckCircle2 className="w-10 h-10" />
            </div>
            <h1 className="text-2xl sm:text-3xl font-black text-slate-900 dark:text-white">
              Emergency Request Dispatched!
            </h1>
            <p className="text-sm text-slate-600 dark:text-slate-300 max-w-lg mx-auto">
              Your request has been registered under ID{' '}
              <span className="font-mono font-bold text-rose-600 dark:text-rose-400">
                {createdRequest.requestId}
              </span>
              . Matching nearby voluntary donors are receiving notification alerts now.
            </p>
          </div>

          {/* Summary Box */}
          <div className="bg-slate-50 dark:bg-slate-700/40 rounded-2xl p-6 border border-slate-100 dark:border-slate-700 grid grid-cols-2 sm:grid-cols-4 gap-4 text-xs">
            <div>
              <span className="text-slate-400 block font-medium">Blood Group:</span>
              <span className="text-base font-extrabold text-slate-900 dark:text-white">
                {createdRequest.bloodGroup}
              </span>
            </div>
            <div>
              <span className="text-slate-400 block font-medium">Units Required:</span>
              <span className="text-base font-extrabold text-slate-900 dark:text-white">
                {createdRequest.unitsRequired} Units
              </span>
            </div>
            <div>
              <span className="text-slate-400 block font-medium">Hospital:</span>
              <span className="text-sm font-bold text-slate-900 dark:text-white truncate block">
                {createdRequest.hospitalName}
              </span>
            </div>
            <div>
              <span className="text-slate-400 block font-medium">Current Status:</span>
              <span className="inline-block mt-0.5 px-2.5 py-0.5 rounded-full text-[10px] font-bold bg-amber-100 dark:bg-amber-950/80 text-amber-800 dark:text-amber-300">
                {createdRequest.status}
              </span>
            </div>
          </div>

          {/* WhatsApp Share & Viral CTA */}
          <div className="space-y-3 pt-2">
            <h3 className="text-xs font-bold uppercase tracking-wider text-slate-500 dark:text-slate-400 text-center">
              Amplify Your Request Across Family & Friends
            </h3>
            <div className="flex flex-col sm:flex-row items-center justify-center gap-3">
              <a
                href={getWhatsAppShareUrl()}
                target="_blank"
                rel="noreferrer"
                className="w-full sm:w-auto inline-flex items-center justify-center space-x-2 px-6 py-3 rounded-xl bg-emerald-600 hover:bg-emerald-700 text-white font-bold text-sm shadow transition"
              >
                <Share2 className="w-4 h-4" />
                <span>Share Urgently on WhatsApp</span>
              </a>

              <button
                onClick={handleCopyShare}
                className="w-full sm:w-auto inline-flex items-center justify-center space-x-2 px-6 py-3 rounded-xl bg-slate-100 dark:bg-slate-700 hover:bg-slate-200 dark:hover:bg-slate-600 text-slate-700 dark:text-slate-200 font-bold text-sm transition"
              >
                <Copy className="w-4 h-4" />
                <span>{copiedLink ? 'Link Copied!' : 'Copy Shareable Link'}</span>
              </button>
            </div>
          </div>

          <div className="flex items-center justify-between pt-6 border-t border-slate-100 dark:border-slate-700 text-xs">
            <button
              onClick={() => {
                setCreatedRequest(null);
                setPatientName('');
              }}
              className="text-slate-500 hover:text-slate-800 dark:hover:text-slate-200"
            >
              Post Another Request
            </button>
            <Link
              to="/patient-dashboard"
              className="inline-flex items-center space-x-1 font-bold text-rose-600 dark:text-rose-400 hover:underline"
            >
              <span>Go to Request Dashboard</span>
              <ArrowRight className="w-4 h-4" />
            </Link>
          </div>
        </div>
      ) : (
        /* Request Form */
        <form
          onSubmit={handleSubmit}
          className="bg-white dark:bg-slate-800 rounded-3xl p-6 sm:p-10 border border-slate-200 dark:border-slate-700/80 shadow-sm space-y-6"
        >
          <div>
            <span className="text-xs font-bold uppercase tracking-wider text-rose-600 dark:text-rose-400">
              Immediate Assistance
            </span>
            <h1 className="text-2xl sm:text-3xl font-black text-slate-900 dark:text-white mt-1">
              Post Emergency Blood Request
            </h1>
            <p className="text-xs sm:text-sm text-slate-500 dark:text-slate-400 mt-1">
              Provide complete hospital and patient details so that nearby voluntary donors can respond immediately.
            </p>
          </div>

          {error && (
            <div className="p-4 bg-rose-50 dark:bg-rose-950/60 border border-rose-200 dark:border-rose-800 rounded-xl text-xs text-rose-700 dark:text-rose-300">
              {error}
            </div>
          )}

          {/* Section 1: Patient & Blood Requirement */}
          <div className="space-y-4">
            <h3 className="text-xs font-bold uppercase tracking-wider text-slate-400 border-b border-slate-100 dark:border-slate-700/60 pb-2">
              1. Patient & Blood Requirement
            </h3>
            <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
              <div className="sm:col-span-1">
                <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                  Patient Full Name <span className="text-rose-500">*</span>
                </label>
                <input
                  type="text"
                  required
                  value={patientName}
                  onChange={(e) => setPatientName(e.target.value)}
                  placeholder="Patient Name"
                  className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-3 focus:ring-2 focus:ring-rose-500 focus:outline-none"
                />
              </div>

              <div>
                <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                  Required Blood Group <span className="text-rose-500">*</span>
                </label>
                <select
                  value={bloodGroup}
                  onChange={(e) => setBloodGroup(e.target.value as BloodGroup)}
                  className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-3 focus:ring-2 focus:ring-rose-500 focus:outline-none"
                >
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

              <div>
                <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                  Units Required <span className="text-rose-500">*</span>
                </label>
                <input
                  type="number"
                  min={1}
                  max={10}
                  required
                  value={unitsRequired}
                  onChange={(e) => setUnitsRequired(Number(e.target.value))}
                  className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-3 focus:ring-2 focus:ring-rose-500 focus:outline-none"
                />
              </div>
            </div>

            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div>
                <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                  Emergency Urgency Level <span className="text-rose-500">*</span>
                </label>
                <div className="grid grid-cols-3 gap-2">
                  {(['CRITICAL', 'HIGH', 'NORMAL'] as RequestPriority[]).map((p) => (
                    <button
                      key={p}
                      type="button"
                      onClick={() => setPriority(p)}
                      className={`py-2 px-3 rounded-xl text-xs font-bold border transition ${
                        priority === p
                          ? p === 'CRITICAL'
                            ? 'bg-rose-600 text-white border-rose-600'
                            : p === 'HIGH'
                            ? 'bg-amber-600 text-white border-amber-600'
                            : 'bg-blue-600 text-white border-blue-600'
                          : 'bg-slate-50 dark:bg-slate-700 text-slate-600 dark:text-slate-300 border-slate-200 dark:border-slate-600'
                      }`}
                    >
                      {p}
                    </button>
                  ))}
                </div>
              </div>

              <div>
                <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                  Needed By Date & Time <span className="text-rose-500">*</span>
                </label>
                <input
                  type="datetime-local"
                  required
                  value={requiredBy}
                  onChange={(e) => setRequiredBy(e.target.value)}
                  className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-2.5 focus:ring-2 focus:ring-rose-500 focus:outline-none"
                />
              </div>
            </div>
          </div>

          {/* Section 2: Hospital & Location Details */}
          <div className="space-y-4 pt-2">
            <h3 className="text-xs font-bold uppercase tracking-wider text-slate-400 border-b border-slate-100 dark:border-slate-700/60 pb-2">
              2. Hospital & Location Details
            </h3>
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div>
                <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                  Hospital Name <span className="text-rose-500">*</span>
                </label>
                <input
                  type="text"
                  required
                  value={hospitalName}
                  onChange={(e) => setHospitalName(e.target.value)}
                  placeholder="E.g., Manipal Hospital, Whitefield"
                  className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-3 focus:ring-2 focus:ring-rose-500 focus:outline-none"
                />
              </div>

              <div>
                <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                  Hospital Full Address / Ward <span className="text-rose-500">*</span>
                </label>
                <input
                  type="text"
                  required
                  value={hospitalAddress}
                  onChange={(e) => setHospitalAddress(e.target.value)}
                  placeholder="E.g., HAL Old Airport Rd, Ward 3B, ICU"
                  className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-3 focus:ring-2 focus:ring-rose-500 focus:outline-none"
                />
              </div>
            </div>

            <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
              <div>
                <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                  City <span className="text-rose-500">*</span>
                </label>
                <input
                  type="text"
                  required
                  value={city}
                  onChange={(e) => setCity(e.target.value)}
                  className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-3 focus:ring-2 focus:ring-rose-500 focus:outline-none"
                />
              </div>

              <div>
                <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                  State <span className="text-rose-500">*</span>
                </label>
                <select
                  value={state}
                  onChange={(e) => setState(e.target.value)}
                  className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-3 focus:ring-2 focus:ring-rose-500 focus:outline-none"
                >
                  {indianStates.map((st) => (
                    <option key={st} value={st}>
                      {st}
                    </option>
                  ))}
                </select>
              </div>

              <div>
                <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                  6-Digit PIN Code <span className="text-rose-500">*</span>
                </label>
                <input
                  type="text"
                  maxLength={6}
                  required
                  value={pinCode}
                  onChange={(e) => setPinCode(e.target.value)}
                  placeholder="560017"
                  className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-3 focus:ring-2 focus:ring-rose-500 focus:outline-none font-mono"
                />
              </div>
            </div>
          </div>

          {/* Section 3: Communication & Notes */}
          <div className="space-y-4 pt-2">
            <h3 className="text-xs font-bold uppercase tracking-wider text-slate-400 border-b border-slate-100 dark:border-slate-700/60 pb-2">
              3. Preferences & Medical Notes
            </h3>
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div>
                <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                  Contact Preference
                </label>
                <select
                  value={contactPreference}
                  onChange={(e) => setContactPreference(e.target.value as ContactPreference)}
                  className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-3 focus:ring-2 focus:ring-rose-500 focus:outline-none"
                >
                  <option value="PHONE">Direct Phone Call</option>
                  <option value="WHATSAPP">WhatsApp Messages</option>
                  <option value="IN_APP">In-App Chat / Notification Only</option>
                </select>
              </div>

              <div>
                <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
                  Additional Notes (Case / Diagnosis)
                </label>
                <input
                  type="text"
                  value={additionalNotes}
                  onChange={(e) => setAdditionalNotes(e.target.value)}
                  placeholder="E.g., Emergency dengue platelets or planned heart surgery"
                  className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-700 text-slate-900 dark:text-white p-3 focus:ring-2 focus:ring-rose-500 focus:outline-none"
                />
              </div>
            </div>
          </div>

          {/* Mandatory Checkbox */}
          <div className="pt-2">
            <label className="flex items-start space-x-3 text-xs text-slate-600 dark:text-slate-300 cursor-pointer">
              <input
                type="checkbox"
                required
                checked={consentAcknowledged}
                onChange={(e) => setConsentAcknowledged(e.target.checked)}
                className="mt-0.5 rounded text-rose-600 focus:ring-rose-500"
              />
              <span className="leading-relaxed">
                I declare that this is a genuine medical emergency for the specified patient at the named hospital. I
                understand that BloodBridge is an intermediary platform and that all medical cross-matching, testing,
                and transfusions must be executed by hospital medical professionals.
              </span>
            </label>
          </div>

          <div className="pt-4 border-t border-slate-100 dark:border-slate-700 flex justify-end">
            <button
              type="submit"
              disabled={loading}
              className="w-full sm:w-auto inline-flex items-center justify-center space-x-2 px-8 py-3.5 rounded-2xl bg-rose-600 hover:bg-rose-700 text-white font-bold text-sm shadow-lg shadow-rose-600/30 transition disabled:opacity-50"
            >
              {loading ? (
                <span>Dispatching Alerts...</span>
              ) : (
                <>
                  <AlertCircle className="w-5 h-5" />
                  <span>Broadcast Emergency Request</span>
                </>
              )}
            </button>
          </div>
        </form>
      )}
    </div>
  );
};
