import React, { createContext, useContext, useState, useEffect } from 'react';
import { User, Role } from '../types';
import { setAccessToken, getStoredAccessToken, authApi } from '../services/api';

interface AuthContextType {
  user: User | null;
  token: string | null;
  login: (email: string, pass: string) => Promise<User>;
  register: (payload: any) => Promise<User>;
  registerDonor: (payload: any) => Promise<User>;
  registerHospital: (payload: any) => Promise<User>;
  logout: () => void;
  loading: boolean;
  isAuthenticated: boolean;
  isDonor: boolean;
  isPatient: boolean;
  isHospital: boolean;
  isAdmin: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(getStoredAccessToken());
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const savedUser = localStorage.getItem('bb_user');
    if (savedUser && token) {
      try {
        const parsed = JSON.parse(savedUser);
        setUser({
          ...parsed,
          name: parsed.fullName || parsed.name || 'User',
        });
      } catch (e) {
        localStorage.removeItem('bb_user');
      }
    }
    setLoading(false);
  }, [token]);

  const login = async (email: string, pass: string): Promise<User> => {
    const data = await authApi.login(email, pass);
    const loggedUser: User = {
      id: data.userId,
      email: data.email,
      phone: data.phone || '',
      role: data.role,
      status: data.status,
      fullName: data.fullName,
      name: data.fullName || 'User',
      bloodGroup: data.bloodGroup,
      city: data.city,
      profileId: data.profileId,
    };
    setUser(loggedUser);
    setToken(data.accessToken);
    setAccessToken(data.accessToken);
    localStorage.setItem('bb_refresh_token', data.refreshToken);
    localStorage.setItem('bb_user', JSON.stringify(loggedUser));
    return loggedUser;
  };

  const register = async (payload: any): Promise<User> => {
    const data = await authApi.register(payload);
    const registeredUser: User = {
      id: data.userId,
      email: data.email,
      phone: data.phone || '',
      role: data.role,
      status: data.status,
      fullName: data.fullName,
      name: data.fullName || 'User',
      bloodGroup: data.bloodGroup,
      city: data.city,
      profileId: data.profileId,
    };
    setUser(registeredUser);
    setToken(data.accessToken);
    setAccessToken(data.accessToken);
    localStorage.setItem('bb_refresh_token', data.refreshToken);
    localStorage.setItem('bb_user', JSON.stringify(registeredUser));
    return registeredUser;
  };

  const registerDonor = async (payload: any): Promise<User> => {
    return register({ ...payload, role: 'ROLE_DONOR' });
  };

  const registerHospital = async (payload: any): Promise<User> => {
    return register({ ...payload, role: 'ROLE_HOSPITAL' });
  };

  const logout = () => {
    setUser(null);
    setToken(null);
    setAccessToken(null);
    localStorage.removeItem('bb_access_token');
    localStorage.removeItem('bb_refresh_token');
    localStorage.removeItem('bb_user');
  };

  const isRole = (targetRole: string) => {
    if (!user) return false;
    return user.role === targetRole || user.role === `ROLE_${targetRole}`;
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        token,
        login,
        register,
        registerDonor,
        registerHospital,
        logout,
        loading,
        isAuthenticated: !!user && !!token,
        isDonor: isRole('DONOR'),
        isPatient: isRole('PATIENT'),
        isHospital: isRole('HOSPITAL'),
        isAdmin: isRole('ADMIN'),
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) throw new Error('useAuth must be used within AuthProvider');
  return context;
};
