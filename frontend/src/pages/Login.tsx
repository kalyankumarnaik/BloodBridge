import React, { useState } from 'react';
import { useNavigate, useLocation, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { Heart, Lock, Mail, ArrowRight, ShieldCheck, UserCheck, Sparkles, Eye, EyeOff } from 'lucide-react';

export const Login: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { login } = useAuth();

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const from = (location.state as any)?.from?.pathname || null;

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      const user = await login(username.trim(), password);
      if (from) {
        navigate(from, { replace: true });
      } else {
        const role = user.role.replace('ROLE_', '');
        switch (role) {
          case 'ADMIN':
            navigate('/admin', { replace: true });
            break;
          case 'HOSPITAL':
            navigate('/hospital', { replace: true });
            break;
          case 'DONOR':
            navigate('/donor-dashboard', { replace: true });
            break;
          case 'PATIENT':
            navigate('/patient-dashboard', { replace: true });
            break;
          default:
            navigate('/', { replace: true });
        }
      }
    } catch (err: any) {
      console.error('Login error:', err);
      if (err.response?.status === 429) {
        setError('Account temporarily locked due to multiple failed attempts. Please try again after 15 minutes.');
      } else {
        setError(err.response?.data?.message || 'Invalid email/phone or password.');
      }
    } finally {
      setLoading(false);
    }
  };

  const handleDemoFill = (u: string, p: string) => {
    setUsername(u);
    setPassword(p);
  };

  return (
    <div className="min-h-[80vh] flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full space-y-8">
        <div className="text-center space-y-2">
          <div className="w-12 h-12 bg-rose-600 rounded-2xl flex items-center justify-center text-white mx-auto shadow-lg shadow-rose-600/30">
            <Heart className="w-6 h-6 fill-white" />
          </div>
          <h1 className="text-3xl font-black text-slate-900 dark:text-white tracking-tight">
            Sign In to BloodBridge
          </h1>
          <p className="text-xs sm:text-sm text-slate-500 dark:text-slate-400">
            Access your donor profile, hospital blood availability, or emergency requests.
          </p>
        </div>

        {/* Demo Account Quick-Fill Card */}
        <div className="bg-slate-50 dark:bg-slate-800/60 rounded-2xl p-4 border border-slate-200 dark:border-slate-700/80 space-y-2">
          <div className="flex items-center space-x-1.5 text-xs font-bold text-slate-700 dark:text-slate-300">
            <Sparkles className="w-3.5 h-3.5 text-amber-500" />
            <span>1-Click Demo Accounts (Local Dev Profile):</span>
          </div>
          <div className="grid grid-cols-2 gap-2 text-[11px]">
            <button
              type="button"
              onClick={() => handleDemoFill('donor.rahul@example.com', 'Donor@12345')}
              className="p-2 rounded-xl bg-white dark:bg-slate-700 hover:bg-rose-50 dark:hover:bg-slate-600 border border-slate-200 dark:border-slate-600 font-semibold text-slate-700 dark:text-slate-200 text-left transition"
            >
              🩸 Donor (Rahul S.)
            </button>
            <button
              type="button"
              onClick={() => handleDemoFill('patient.priya@example.com', 'Patient@12345')}
              className="p-2 rounded-xl bg-white dark:bg-slate-700 hover:bg-rose-50 dark:hover:bg-slate-600 border border-slate-200 dark:border-slate-600 font-semibold text-slate-700 dark:text-slate-200 text-left transition"
            >
              🏥 Patient (Priya S.)
            </button>
            <button
              type="button"
              onClick={() => handleDemoFill('hospital.admin@citycare.org', 'Hospital@12345')}
              className="p-2 rounded-xl bg-white dark:bg-slate-700 hover:bg-rose-50 dark:hover:bg-slate-600 border border-slate-200 dark:border-slate-600 font-semibold text-slate-700 dark:text-slate-200 text-left transition"
            >
              🏢 Hospital (City Care)
            </button>
            <button
              type="button"
              onClick={() => handleDemoFill('admin@bloodbridge.in', 'Admin@12345')}
              className="p-2 rounded-xl bg-white dark:bg-slate-700 hover:bg-rose-50 dark:hover:bg-slate-600 border border-slate-200 dark:border-slate-600 font-semibold text-slate-700 dark:text-slate-200 text-left transition"
            >
              🛡️ Administrator
            </button>
          </div>
        </div>

        {error && (
          <div className="p-3.5 bg-rose-50 dark:bg-rose-950/60 border border-rose-200 dark:border-rose-800 rounded-xl text-xs text-rose-700 dark:text-rose-300">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300 mb-1">
              Email Address or Mobile Number
            </label>
            <div className="relative">
              <Mail className="w-4 h-4 text-slate-400 absolute left-3 top-3.5" />
              <input
                type="text"
                required
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                placeholder="name@example.com or 9876543210"
                className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-800 text-slate-900 dark:text-white pl-10 pr-3 py-2.5 focus:ring-2 focus:ring-rose-500 focus:outline-none"
              />
            </div>
          </div>

          <div>
            <div className="flex items-center justify-between mb-1">
              <label className="block text-xs font-bold uppercase tracking-wide text-slate-700 dark:text-slate-300">
                Password
              </label>
              <Link
                to="/forgot-password"
                className="text-xs text-rose-600 dark:text-rose-400 hover:underline font-semibold"
              >
                Forgot Password?
              </Link>
            </div>
            <div className="relative">
              <Lock className="w-4 h-4 text-slate-400 absolute left-3 top-3.5" />
              <input
                type={showPassword ? 'text' : 'password'}
                required
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                placeholder="••••••••"
                className="w-full text-sm rounded-xl border border-slate-300 dark:border-slate-600 bg-white dark:bg-slate-800 text-slate-900 dark:text-white pl-10 pr-10 py-2.5 focus:ring-2 focus:ring-rose-500 focus:outline-none"
              />
              <button
                type="button"
                onClick={() => setShowPassword(!showPassword)}
                className="absolute right-3 top-3 text-slate-400 hover:text-slate-600 dark:hover:text-slate-200"
              >
                {showPassword ? <EyeOff className="w-4 h-4" /> : <Eye className="w-4 h-4" />}
              </button>
            </div>
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full inline-flex items-center justify-center space-x-2 py-3 px-4 rounded-xl text-sm font-bold text-white bg-rose-600 hover:bg-rose-700 shadow-md transition disabled:opacity-50"
          >
            {loading ? <span>Signing In...</span> : <span>Sign In to BloodBridge</span>}
          </button>
        </form>

        <div className="text-center space-y-3 pt-4 border-t border-slate-200 dark:border-slate-700 text-xs text-slate-500">
          <p>
            Don&apos;t have an account?{' '}
            <Link to="/register-donor" className="font-bold text-rose-600 dark:text-rose-400 hover:underline">
              Register as Donor
            </Link>
            {' • '}
            <Link to="/hospital-register" className="font-bold text-slate-700 dark:text-slate-300 hover:underline">
              Hospital Portal
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
};
