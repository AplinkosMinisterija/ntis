export enum CwFilStatus {
  cw_fil_pending = 'CW_FIL_PENDING',
  cw_fil_pending_err = 'CW_FIL_PENDING_ERR',
  cw_fil_final = 'CW_FIL_FINAL',
  cw_fil_err_proccessing = 'CW_FIL_ERR_PROCESSING',
}

export enum NtisFacilityOwnerType {
  OWNER = 'OWNER',
  SERVICE_PROVIDER = 'SERVICE_PROVIDER',
  SELF_ASSIGNED = 'SELF_ASSIGNED',
}

export enum OrderStatus {
  confirmed = 'ORD_STS_CONFIRMED',
  cancelled = 'ORD_STS_CANCELLED',
  rejected = 'ORD_STS_REJECTED',
  submitted = 'ORD_STS_SUBMITTED',
  finished = 'ORD_STS_FINISHED',
  cancelledSystem = 'ORD_STS_CANCELLED_SYSTEM',
}

export enum ServiceType {
  serviceProvider = 'PASLAUG',
  waterManager = 'VANDEN',
}

export enum ServiceRequestStatus {
  submitted = 'SUBMITTED',
  rejected = 'REJECTED',
  confirmed = 'CONFIRMED',
}

export enum SewageDeliveryStatus {
  submitted = 'SWG_DLV_STS_SUBMITTED',
  rejected = 'SWG_DLV_STS_REJECTED',
  confirmed = 'SWG_DLV_STS_CONFIRMED',
  used = 'SWG_DLV_STS_USED',
}

export enum ServiceItemType {
  sewageRemoval = 'VEZIMAS',
  maintenance = 'PRIEZIURA',
  tests = 'TYRIMAI',
  treatment = 'VALYMAS',
  installation = 'MONTAVIMAS',
}

export enum ServiceItemStatus {
  registered = 'REGISTRUOTA',
  notRegistered = 'NETEIKIAMA',
}

export enum NtisResearchType {
  BIOCHEMICAL = 'BIOCHEMICAL',
  DROWNING = 'DROWNING',
  NITROGEN = 'NITROGEN',
  PHOSPHORUS = 'PHOSPHORUS',
}

export enum GraphDataPeriod {
  lastSevenDays = 'GRP_DAT_PRD_LAST_7_DAYS',
  thisWeek = 'GRP_DAT_PRD_THIS_WEEK',
  lastWeek = 'GRP_DAT_PRD_LAST_WEEK',
  thisMonth = 'GRP_DAT_PRD_THIS_MONTH',
  previousMonth = 'GRP_DAT_PRD_LAST_MONTH',
  thisYear = 'GRP_DAT_PRD_THIS_YEAR',
}

export enum NtisFaciUpdateReq {
  SUBMITTED = 'SUBMITTED',
  CANCELLED = 'CANCELLED',
  REJECTED = 'REJECTED',
  CONFIRMED = 'CONFIRMED',
}

export enum NtisBuildingAgreementStatus {
  WAITING = 'WAITING',
  CONFIRMED = 'CONFIRMED',
  REJECT = 'REJECTED',
  req_sts_confirmed = 'REQ_STS_CONFIRMED',
}

export enum NtisNtfRefType {
  SRV_REQ = 'SRV_REQ',
  CONTRACT = 'CONTRACT',
  ORDER = 'ORDER',
  DELIVERY = 'DELIVERY',
  SYSTEM = 'SYSTEM',
  RESEARCH = 'RESEARCH',
  FUA_AGREEMENT = 'FUA_AGREEMENT',
}

export enum NtisOrgType {
  PASLAUG = 'PASLAUG',
  VANDEN = 'VANDEN',
  PASLAUG_VANDEN = 'PASLAUG_VANDEN',
  INST = 'INST',
  INST_LT = 'INST_LT',
}

export enum NtisAddressMapType {
  SAVIVALD = 'SAVIVALD',
  SENIUN = 'SENIUN',
  VIETOV = 'VIETOV',
  GATVE = 'GATVE',
}

/** NTIS_INTS_STATUS classifier codes */
export enum NtisIntsStatus {
  REGISTERED = 'REGISTERED',
  CONFIRMED = 'CONFIRMED',
  CLOSED = 'CLOSED',
}

/** NTIS_TYPE_WASTEWATER_TREATMENT classifier codes */
export enum NtisTypeWastewaterTreatment {
  CENTRALIZED = 'CENTRALIZED',
  LOCAL = 'LOCAL',
}

/** NTIS_WTF_TYPE classifier codes */
export enum NtisWtfType {
  BIO = 'BIO',
  SEPTIC = 'SEPTIC',
  RESERVOIR = 'RESERVOIR',
  PORTABLE_RESERVOIR = 'PORTABLE_RESERVOIR',
}

export enum MessageSubject {
  MSG_SBJ_APPLICATION_SUBMITTED = 'MSG_SBJ_APPLICATION_SUBMITTED',
  MSG_SBJ_APPLICATION_REJECTED = 'MSG_SBJ_APPLICATION_REJECTED',
  MSG_SBJ_APPLICATION_ACCEPTED = 'MSG_SBJ_APPLICATION_ACCEPTED',
  MSG_SBJ_ACCESS_RESTRICTED = 'MSG_SBJ_ACCESS_RESTRICTED',
  MSG_SBJ_SYSTEM_INFO = 'MSG_SBJ_SYSTEM_INFO',
  MSG_SBJ_ORDER_SUBMITTED = 'MSG_SBJ_ORDER_SUBMITTED',
  MSG_SBJ_ORDER_CANCELLED = 'MSG_SBJ_ORDER_CANCELLED',
  MSG_SBJ_ORDER_REJECTED = 'MSG_SBJ_ORDER_REJECTED',
  MSG_SBJ_ORDER_CONFIRMED = 'MSG_SBJ_ORDER_CONFIRMED',
  MSG_SBJ_ORDER_UPDATED = 'MSG_SBJ_ORDER_UPDATED',
  MSG_SBJ_ORDER_FINISHED = 'MSG_SBJ_ORDER_FINISHED',
  MSG_SBJ_DELIVERY_SUBMITTED = 'MSG_SBJ_DELIVERY_SUBMITTED',
  MSG_SBJ_DELIVERY_REJECTED = 'MSG_SBJ_DELIVERY_REJECTED',
  MSG_SBJ_DELIVERY_CONFIRMED = 'MSG_SBJ_DELIVERY_CONFIRMED',
  MSG_SBJ_DELIVERY_CANCELLED = 'MSG_SBJ_DELIVERY_CANCELLED',
  MSG_SBJ_AGREEMENT_REJECTED = 'MSG_SBJ_AGREEMENT_REJECTED',
  MSG_SBJ_AGREEMENT_SIGNED = 'MSG_SBJ_AGREEMENT_SIGNED',
  MSG_SBJ_AGREEMENT_COMMENTED = 'MSG_SBJ_AGREEMENT_COMMENTED',
  MSG_SBJ_AGREEMENT_CANCELLED = 'MSG_SBJ_AGREEMENT_CANCELLED',
  MSG_SBJ_AGREEMENT_SUSPENDED = 'MSG_SBJ_AGREEMENT_SUSPENDED',
}

/** NTIS_PAGE_TEMPLATE_TYPE classifier codes */
export enum NtisPageTemplateType {
  INT_DASHBOARD = 'INT_DASHBOARD',
  HOME = 'HOME',
  TERMS_AND_CONDITIONS = 'TERMS_AND_CONDITIONS',
}

export enum OrdFilStatus {
  ord_fil_pending = 'ORD_FIL_PENDING',
  ord_fil_pending_err = 'ORD_FIL_PENDING_ERR',
  ord_fil_final = 'ORD_FIL_FINAL',
  ord_fil_err_proccessing = 'ORD_FIL_ERR_PROCCESSING',
}
