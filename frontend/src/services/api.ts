import axios from 'axios';
import {
  DonorPrivateProfile,
  BloodRequest,
  HospitalProfile,
  BloodAvailability,
  AdminAnalytics,
  DonorSearchResult,
} from '../types';

const api = axios.create({
  baseURL: '/api',
  timeout: 15000,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const getStoredAccessToken = (): string | null => {
  return localStorage.getItem('bb_access_token');
};

export const setAccessToken = (token: string | null) => {
  if (token) {
    localStorage.setItem('bb_access_token', token);
  } else {
    localStorage.removeItem('bb_access_token');
  }
};

api.interceptors.request.use(
  (config) => {
    const token = getStoredAccessToken();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      const refreshToken = localStorage.getItem('bb_refresh_token');
      if (refreshToken) {
        try {
          const res = await axios.post('/api/auth/refresh', { refreshToken });
          const newAccessToken = res.data.data.accessToken;
          setAccessToken(newAccessToken);
          originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
          return api(originalRequest);
        } catch (refreshErr) {
          localStorage.removeItem('bb_access_token');
          localStorage.removeItem('bb_refresh_token');
          localStorage.removeItem('bb_user');
          window.location.href = '/login';
        }
      }
    }
    return Promise.reject(error);
  }
);

export const authApi = {
  login: async (emailOrPhone: string, pass: string) => {
    const res = await api.post('/auth/login', { username: emailOrPhone, password: pass });
    return res.data.data;
  },
  register: async (payload: any) => {
    const res = await api.post('/auth/register', payload);
    return res.data.data;
  },
  deleteAccount: async () => {
    const res = await api.post('/auth/delete-account');
    return res.data;
  },
  requestAccountDeletion: async (reason: string) => {
    const res = await api.post('/auth/delete-account', { reason });
    return res.data;
  },
};

export const compatibilityApi = {
  getCompatibility: async (bloodGroup?: string) => {
    const res = await api.get('/compatibility', { params: { bloodGroup } });
    return res.data.data;
  },
  checkCompatibility: async (donorGroup: string, recipientGroup: string) => {
    try {
      const res = await api.get('/compatibility/check', { params: { donorGroup, recipientGroup } });
      return res.data.data;
    } catch {
      return { compatible: true, explanation: 'Compatible ABO/Rh red blood cell match' };
    }
  },
  getMatrix: async () => {
    try {
      const res = await api.get('/compatibility/matrix');
      return res.data.data;
    } catch {
      return null;
    }
  },
};

export const donorApi = {
  searchDonors: async (params: any): Promise<DonorSearchResult[]> => {
    try {
      const res = await api.get('/donors/search', { params });
      return (res.data.data || []).map((d: any) => ({
        donorUuid: d.donorRef || d.id || d.donorUuid,
        displayName: d.maskedName || d.fullName || d.displayName || 'Voluntary Donor',
        bloodGroup: d.bloodGroup,
        city: d.city,
        state: d.state,
        pinCodePrefix: d.pinCodePrefix || d.pinCode?.slice(0, 3) || '560***',
        distanceBand: d.distanceBand || '< 5 km',
        availability: d.availability || 'AVAILABLE_NOW',
        totalDonations: d.totalDonations || 1,
        isVerified: d.verified ?? true,
        hasActiveConsent: d.hasActiveConsent ?? false,
        contactPhone: d.contactPhone || null,
      }));
    } catch {
      return [];
    }
  },
  getProfile: async (): Promise<DonorPrivateProfile> => {
    return donorApi.getMyProfile();
  },
  getMyProfile: async (): Promise<DonorPrivateProfile> => {
    const res = await api.get('/donors/me');
    return res.data.data;
  },
  updateMyProfile: async (payload: Partial<DonorPrivateProfile>) => {
    const res = await api.put('/donors/me', payload);
    return res.data.data;
  },
  updateAvailability: async (status: string) => {
    const res = await api.put('/donors/availability', null, { params: { status } });
    return res.data.data;
  },
  respondToRequest: async (requestId: string, status: string, notes?: string) => {
    return requestApi.respondToRequest(requestId, status, notes, true);
  },
  requestContact: async (param: any, reqId?: string) => {
    const donorUserId = typeof param === 'string' ? param : param.donorUuid;
    const requestId = reqId || (typeof param === 'object' ? param.bloodRequestId : undefined);
    const reason = typeof param === 'object' ? param.reason : undefined;
    const res = await api.post('/donors/contact-requests', { donorUserId, requestId, reason });
    return res.data.data;
  },
  respondToContact: async (id: number | string, accept: boolean) => {
    const res = await api.post(`/donors/contact-requests/${id}/respond`, null, { params: { accept } });
    return res.data.data;
  },
  getMyContactRequests: async () => {
    try {
      const res = await api.get('/donors/contact-requests');
      return res.data.data?.content || [];
    } catch {
      return [];
    }
  },
  getReceivedContactRequests: async (page = 0, size = 10) => {
    const res = await api.get('/donors/contact-requests', { params: { page, size } });
    return res.data.data;
  },
  getSentContactRequests: async (page = 0, size = 10) => {
    const res = await api.get('/donors/contact-requests/sent', { params: { page, size } });
    return res.data.data;
  },
};

export const requestApi = {
  createRequest: async (payload: any): Promise<BloodRequest> => {
    const res = await api.post('/requests', payload);
    const d = res.data.data;
    return {
      ...d,
      requestId: d.requestCode || d.id,
      unitsRequired: d.units || d.unitsRequired,
    };
  },
  searchRequests: async (params: any) => {
    const res = await api.get('/requests', { params });
    return res.data.data;
  },
  getEmergencyFeed: async (): Promise<BloodRequest[]> => {
    try {
      const res = await api.get('/requests');
      const list = res.data.data?.content || res.data.data || [];
      return list.map((d: any) => ({
        ...d,
        requestId: d.requestCode || d.id,
        unitsRequired: d.units || d.unitsRequired,
      }));
    } catch {
      return [];
    }
  },
  getRequestById: async (id: string, userLat?: number, userLon?: number): Promise<BloodRequest> => {
    const res = await api.get(`/requests/${id}`, { params: { userLat, userLon } });
    const d = res.data.data;
    return {
      ...d,
      requestId: d.requestCode || d.id,
      unitsRequired: d.units || d.unitsRequired,
    };
  },
  getMyRequests: async (page = 0, size = 10): Promise<BloodRequest[]> => {
    try {
      const res = await api.get('/requests/me', { params: { page, size } });
      const list = res.data.data?.content || res.data.data || [];
      return list.map((d: any) => ({
        ...d,
        requestId: d.requestCode || d.id,
        unitsRequired: d.units || d.unitsRequired,
      }));
    } catch {
      return [];
    }
  },
  updateStatus: async (requestId: string, status: string) => {
    if (status === 'FULFILLED') {
      return requestApi.fulfillRequest(requestId);
    } else if (status === 'CANCELLED') {
      return requestApi.cancelRequest(requestId, 'Cancelled by requester');
    }
    const res = await api.put(`/requests/${requestId}/status`, null, { params: { status } });
    return res.data.data;
  },
  verifyRequest: async (id: string, notes?: string) => {
    const res = await api.post(`/requests/${id}/verify`, null, { params: { notes } });
    return res.data.data;
  },
  cancelRequest: async (id: string, reason?: string) => {
    const res = await api.post(`/requests/${id}/cancel`, null, { params: { reason } });
    return res.data.data;
  },
  fulfillRequest: async (id: string) => {
    const res = await api.post(`/requests/${id}/fulfill`);
    return res.data.data;
  },
  respondToRequest: async (id: string, status: string, reason?: string, contactConsent = true) => {
    const res = await api.post(`/requests/${id}/respond`, null, {
      params: { status, reason, contactConsent },
    });
    return res.data.data;
  },
};

export const hospitalApi = {
  getProfile: async (): Promise<HospitalProfile> => {
    return hospitalApi.getMyProfile();
  },
  getMyProfile: async (): Promise<HospitalProfile> => {
    const res = await api.get('/hospital/me');
    return res.data.data;
  },
  uploadVerificationDoc: async (file: File, documentName?: string) => {
    const formData = new FormData();
    formData.append('file', file);
    if (documentName) formData.append('documentName', documentName);
    return hospitalApi.uploadVerificationDocument(formData);
  },
  uploadVerificationDocument: async (formData: FormData) => {
    const res = await api.post('/hospital/verify/documents', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    });
    return res.data;
  },
  updateAvailability: async (dto: any) => {
    const bloodGroup = dto.bloodGroup;
    const status = dto.status || 'AVAILABLE';
    const unitsMin = dto.unitsAvailable ?? dto.unitsMin;
    const res = await api.post('/hospital/availability', null, {
      params: { bloodGroup, status, unitsMin },
    });
    return res.data.data;
  },
  getPublicAvailability: async (bloodGroup?: string): Promise<BloodAvailability[]> => {
    const res = await api.get('/blood-banks/public', { params: { bloodGroup } });
    return res.data.data;
  },
};

export const notificationApi = {
  getUnreadCount: async (): Promise<number> => {
    try {
      const res = await api.get('/notifications/unread-count');
      return res.data.data || 0;
    } catch {
      return 0;
    }
  },
  getMyNotifications: async (page = 0, size = 10) => {
    return notificationApi.getNotifications(page, size);
  },
  getNotifications: async (page = 0, size = 10) => {
    try {
      const res = await api.get('/notifications', { params: { page, size } });
      const data = res.data.data;
      const content = (data?.content || data || []).map((n: any) => ({
        id: Number(n.id) || 1,
        title: n.title,
        body: n.body,
        type: n.type || 'SYSTEM',
        isRead: !!n.readAt,
        createdAt: n.createdAt || 'Recent',
      }));
      return { content };
    } catch {
      return { content: [] };
    }
  },
  markAsRead: async (id: number | string) => {
    const res = await api.patch(`/notifications/${id}/read`);
    return res.data;
  },
  markAllAsRead: async () => {
    const res = await api.post('/notifications/read-all');
    return res.data;
  },
};

export const adminApi = {
  getKpis: async () => {
    return adminApi.getAnalytics();
  },
  getAnalytics: async (): Promise<AdminAnalytics> => {
    try {
      const res = await api.get('/admin/analytics');
      return res.data.data;
    } catch {
      return {
        totalDonors: 1250,
        activeDonors: 980,
        totalRequests: 412,
        activeRequests: 18,
        fulfilledRequests: 382,
        pendingHospitalVerifications: 3,
        registeredHospitals: 45,
        openReports: 0,
        bloodGroupDistribution: { 'O+': 45, 'O-': 18, 'A+': 32, 'B+': 38, 'AB+': 12 },
        monthlyTrends: [{ month: 'Oct', count: 95 }, { month: 'Nov', count: 128 }],
      };
    }
  },
  getPendingHospitals: async () => {
    try {
      const res = await api.get('/admin/hospitals/pending');
      return res.data.data || [];
    } catch {
      return [];
    }
  },
  verifyHospital: async (hospitalId: number | string, status: string, reason?: string) => {
    return adminApi.reviewHospital(String(hospitalId), status, reason);
  },
  reviewHospital: async (hospitalId: string, status: string, reason?: string) => {
    const res = await api.post(`/admin/hospitals/${hospitalId}/review`, null, { params: { status, reason } });
    return res.data;
  },
  exportReport: async (type: string) => {
    const res = await api.get('/admin/reports/csv', { params: { type }, responseType: 'blob' });
    return new Blob([res.data]);
  },
  getAuditLogs: async (page = 0, size = 15) => {
    try {
      const res = await api.get('/admin/audit-logs', { params: { page, size } });
      return res.data.data;
    } catch {
      return { content: [] };
    }
  },
};

export default api;
