export type Role =
  | 'ROLE_DONOR'
  | 'ROLE_PATIENT'
  | 'ROLE_HOSPITAL'
  | 'ROLE_ADMIN'
  | 'DONOR'
  | 'PATIENT'
  | 'HOSPITAL'
  | 'ADMIN';

export type UserStatus = 'PENDING_VERIFICATION' | 'ACTIVE' | 'SUSPENDED' | 'DELETED';

export type BloodGroup =
  | 'A_POS'
  | 'A_NEG'
  | 'B_POS'
  | 'B_NEG'
  | 'AB_POS'
  | 'AB_NEG'
  | 'O_POS'
  | 'O_NEG'
  | 'A_POSITIVE'
  | 'A_NEGATIVE'
  | 'B_POSITIVE'
  | 'B_NEGATIVE'
  | 'AB_POSITIVE'
  | 'AB_NEGATIVE'
  | 'O_POSITIVE'
  | 'O_NEGATIVE';

export type RequestStatus =
  | 'PENDING'
  | 'VERIFIED'
  | 'ACTIVE'
  | 'DONOR_RESPONDED'
  | 'FULFILLED'
  | 'CANCELLED'
  | 'EXPIRED';

export type RequestPriority = 'CRITICAL' | 'URGENT' | 'HIGH' | 'NORMAL' | 'PLANNED';

export type DonorAvailability =
  | 'AVAILABLE'
  | 'UNAVAILABLE'
  | 'SNOOZED_UNTIL'
  | 'AVAILABLE_NOW'
  | 'ON_CALL'
  | 'INACTIVE';

export type DonorResponseStatus = 'ACCEPTED' | 'DECLINED';
export type ContactRequestStatus = 'PENDING' | 'ACCEPTED' | 'DECLINED' | 'EXPIRED';
export type VerificationStatus = 'SUBMITTED' | 'UNDER_REVIEW' | 'VERIFIED' | 'REJECTED';
export type BloodAvailabilityStatus = 'AVAILABLE' | 'LOW' | 'CRITICAL_LOW' | 'UNAVAILABLE';
export type ContactPreference = 'IN_APP' | 'PHONE' | 'EMAIL' | 'WHATSAPP';

export interface User {
  id: string;
  email: string;
  phone: string;
  role: Role;
  status: UserStatus;
  fullName?: string;
  name?: string;
  bloodGroup?: string;
  city?: string;
  profileId?: string;
}

export interface DonorSearchResult {
  donorUuid: string;
  displayName: string;
  bloodGroup: BloodGroup;
  city: string;
  state: string;
  pinCodePrefix: string;
  distanceBand: string;
  availability: DonorAvailability;
  totalDonations: number;
  isVerified: boolean;
  hasActiveConsent?: boolean;
  contactPhone?: string | null;
}

export interface DonorPublicCard {
  donorRef: string;
  maskedName: string;
  bloodGroup: BloodGroup;
  bloodGroupLabel: string;
  city: string;
  state: string;
  pinCodePrefix: string;
  distanceBand: string;
  exactDistanceKm?: number;
  availability: DonorAvailability;
  verified: boolean;
  lastActiveBucket: string;
  totalDonations: number;
  informationalReminder: string;
}

export interface DonorPrivateProfile {
  id: string;
  userId: string;
  fullName: string;
  email: string;
  phone: string;
  bloodGroup: BloodGroup;
  bloodGroupLabel: string;
  dob: string;
  city: string;
  state: string;
  pinCode: string;
  approxLat?: number;
  approxLon?: number;
  lastDonationDate?: string;
  availability: DonorAvailability;
  showInSearch: boolean;
  shareContactOnAccept: boolean;
  emergencyAlertsEnabled: boolean;
  verifiedByAdmin: boolean;
  totalDonations: number;
  informationalReminder: string;
  createdAt: string;
}

export interface BloodRequest {
  id: string;
  requestCode: string;
  requestId: string;
  requesterId: string;
  requesterName: string;
  hospitalProfileId?: string;
  hospitalName: string;
  hospitalAddress: string;
  city: string;
  state: string;
  pinCode: string;
  lat?: number;
  lon?: number;
  hospitalLatitude?: number;
  hospitalLongitude?: number;
  patientName: string;
  bloodGroup: BloodGroup;
  bloodGroupLabel?: string;
  units: number;
  unitsRequired: number;
  unitsFulfilled?: number;
  priority: RequestPriority;
  requiredBy: string;
  status: RequestStatus;
  contactPreference: ContactPreference;
  notes?: string;
  verifiedByUserId?: string;
  verifiedAt?: string;
  closedReason?: string;
  createdAt: string;
  distanceKm?: number;
  responseCount?: number;
  shareWhatsAppUrl?: string;
  donorResponses?: any[];
}

export interface DonorResponse {
  id: string;
  requestId: string;
  requestCode: string;
  donorUserId: string;
  maskedDonorName: string;
  donorBloodGroup: string;
  response: DonorResponseStatus;
  reason?: string;
  contactConsent: boolean;
  consentExpiresAt?: string;
  revealedPhone?: string;
  createdAt: string;
}

export interface ContactRequest {
  id: string;
  requestId?: string;
  requestCode?: string;
  donorUserId: string;
  donorName: string;
  donorBloodGroup: string;
  requesterUserId: string;
  requesterName: string;
  status: ContactRequestStatus;
  consentRevealedAt?: string;
  expiresAt?: string;
  revealedPhone?: string;
  revealedEmail?: string;
  createdAt: string;
}

export interface HospitalProfile {
  id: string;
  userId: string;
  name: string;
  registrationNumber: string;
  type: string;
  address: string;
  city: string;
  state: string;
  pinCode: string;
  lat?: number;
  lon?: number;
  officialPhone: string;
  contactPerson?: string;
  verificationStatus: VerificationStatus;
  createdAt: string;
}

export interface BloodAvailability {
  id: string;
  hospitalProfileId: string;
  hospitalName: string;
  city: string;
  bloodGroup: BloodGroup;
  bloodGroupLabel: string;
  status: BloodAvailabilityStatus;
  unitsMin?: number;
  unitsMax?: number;
  updatedAt: string;
  stale: boolean;
}

export interface CompatibilityDetails {
  bloodGroup: BloodGroup;
  bloodGroupLabel: string;
  compatibleDonorGroups: BloodGroup[];
  compatibleRecipientGroups: BloodGroup[];
  explanation: string;
  disclaimer: string;
}

export interface InAppNotification {
  id: number;
  title: string;
  body: string;
  type: string;
  isRead: boolean;
  createdAt: string;
}

export interface NotificationItem {
  id: string;
  userId: string;
  type: string;
  channel: string;
  title: string;
  body: string;
  status: string;
  readAt?: string;
  requestId?: string;
  createdAt: string;
}

export interface AdminAnalytics {
  totalDonors: number;
  activeDonors: number;
  totalRequests: number;
  activeRequests: number;
  fulfilledRequests: number;
  pendingHospitalVerifications: number;
  registeredHospitals: number;
  openReports: number;
  bloodGroupDistribution: Record<string, number>;
  monthlyTrends: Array<{ month: string; count: number }>;
}

export interface AdminKpiSummary {
  totalDonors: number | string;
  totalRequests: number | string;
  verifiedHospitals: number | string;
  activeRequests?: number | string;
  fulfilledRequests?: number | string;
}
