/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 2.30.840 on 2024-12-29 19:02:51.

export interface AddressSearch {
    city: number;
    flatNr: string;
    houseNr: string;
    immovablePropertyNo: string;
    latitude: number;
    longitude: number;
    municipality: number;
    street: number;
}

export interface AddressSearchResponse {
    address_id: number;
    full_address_text: string;
    inv_code: string;
    latitude: number;
    longitude: number;
    ntr_building_id: number;
    purpose_of_building: string;
}

export interface AddressSearchResult {
    ad_id: number;
    ads_coordinate_latitude: number;
    ads_coordinate_longitude: number;
    ads_nr: string;
    apl_pat_nr: string;
    re_name_k: string;
    re_type_abbreviation: string;
    rfc_meaning: string;
    str_name: string;
    str_type_abbreviation: string;
}

export interface AdvancedSearchParameter {
    condition: string;
    upperLower: string;
    value: string;
    values: string[];
}

export interface AdvancedSearchParameterStatement {
    paramName: string;
    paramValue: AdvancedSearchParameter;
}

export interface ApiKey {
    api_date_from: Date;
    api_date_to: Date;
    api_id: number;
    api_key: string;
    api_org_id: number;
    api_ou_id: number;
    api_type_code: string;
    api_usr_id: number;
    orgInfo: SprListIdKeyValue[];
    org_id: number;
    org_name: string;
    per_name: string;
    per_surname: string;
    userInfo: SprListIdKeyValue[];
    usr_username: string;
}

export interface AppData {
    languages: IdKeyValuePair[];
    properties: IdKeyValuePair[];
    springProfilesActive: string;
    version: Version;
}

export interface BackendUserSession extends UserDetails {
    apiId: number;
    authenticated: boolean;
    browser: string;
    ip: string;
    role: string;
    sesId: number;
    sesKey: string;
    userPasswordChangeToken: string;
    usrId: number;
}

export interface BrowseFormSearchResult<T> {
    data: T[];
    formActions: FormActions;
    paging: Spr_paging_ot;
}

export interface CacheInfo {
    evictions: number;
    expirations: number;
    gets: number;
    hitPercentage: number;
    hits: number;
    missPercentage: number;
    misses: number;
    name: string;
    puts: number;
    removals: number;
}

export interface CacheManagerFormData {
    cacheInfo: CacheInfo[];
    formActions: FormActions;
}

export interface CacheStatistics {
    diskHits: number;
    diskSize: number;
    diskSizeBytes: number;
    hits: number;
    memoryHits: number;
    memorySize: number;
    memorySizeBytes: number;
    misses: number;
    percentageHits: number;
    percentageHitsStr: string;
    percentageMisses: number;
    percentageMissesStr: string;
    size: number;
    sizeBytes: number;
}

export interface ChangePasswordByEmailRequest {
    email: string;
    lang: string;
}

export interface ChangePasswordRequest {
    newPassword: string;
    oldPassword: string;
}

export interface ChangePasswordWithToken {
    password: string;
    token: string;
}

export interface CheckUserNameExistRequest extends Serializable {
    username: string;
}

export interface ClientSideError {
    clientErrorTime: string;
    errorCode: string;
    errorMessage: string;
    user: string;
}

export interface CodeDictionaryModel {
    rfd_c1_colname: string;
    rfd_c2_colname: string;
    rfd_c3_colname: string;
    rfd_c4_colname: string;
    rfd_c5_colname: string;
    rfd_code_colname: string;
    rfd_code_length: string;
    rfd_code_type: string;
    rfd_d1_colname: string;
    rfd_d2_colname: string;
    rfd_d3_colname: string;
    rfd_d4_colname: string;
    rfd_d5_colname: string;
    rfd_desc_colname: string;
    rfd_n1_colname: string;
    rfd_n2_colname: string;
    rfd_n3_colname: string;
    rfd_n4_colname: string;
    rfd_n5_colname: string;
    rfd_name: string;
    rfd_ref_domain_1: string;
    rfd_ref_domain_1_clsf: string;
    rfd_ref_domain_2: string;
    rfd_ref_domain_2_clsf: string;
    rfd_ref_domain_3: string;
    rfd_ref_domain_3_clsf: string;
    rfd_ref_domain_4: string;
    rfd_ref_domain_4_clsf: string;
    rfd_ref_domain_5: string;
    rfd_ref_domain_5_clsf: string;
}

export interface CreateNewUserRequest extends Serializable {
    password: string;
    username: string;
}

export interface CreatePasswordRequest {
    password: string;
    token: string;
}

export interface EmailVerificationRequest {
    email: string;
    userId: string;
}

export interface FacilityModelAdditionalDetails {
    fam_bds: number;
    fam_chds: number;
    fam_float_material: number;
    fam_nitrogen: number;
    fam_phosphor: number;
    fam_pop_equivalent: number;
    fam_tech_pass: string;
    fil_id: number;
    passport: SprFile;
    rfc_id: number;
}

export interface FaqGroupName {
    rfc_meaning: string;
}

export interface FaqModel {
    attachment: SprFile[];
    facAnswer: string;
    facCode: string;
    facDateFrom: Date;
    facDateTo: Date;
    facGroup: string;
    facId: number;
    facLang: string;
    facQuestion: string;
    facType: string;
}

export interface ForeignKeyParams {
    filterValue: string;
    filterValueModified4Search: string;
    recordCount: number;
}

export interface Form {
    array: string;
    disabled_actions: string[];
    enabled_actions: string[];
    frm_id: string;
    roa_date_from: Date;
    roa_date_to: Date;
    roa_id: string;
    set: string[];
}

export interface Form4RoleRequest {
    frm_id: string;
    roa_id: string;
}

export interface FormActions extends Serializable {
    allActions: string[];
    availableActions: string[];
    disabledActions: string[];
    formName: string;
    menuAvailableActions: string[];
}

export interface FormPredefinedData {
    predefinedFilters: PredefinedFilter[];
}

export interface GoogleLoginRequest {
    authExtData: { [index: string]: any };
    credential: string;
}

export interface GrantedAuthority extends Serializable {
    authority: string;
}

export interface IdKeyValuePair extends Serializable {
    id: string;
    value: string;
}

export interface InitializingBean {
}

export interface Key_values_ot extends Serializable {
    code: string;
    display_text: string;
    key_value: string;
}

export interface LoginRequest {
    authExtData: { [index: string]: any };
    password: string;
    username: string;
}

export interface LoginResult<T> {
    session: T;
    token: string;
}

export interface MailTemplateStructure {
    body: string;
    subject: string;
}

export interface MenuStructure extends Serializable {
    iconName: string;
    level: string;
    link: string;
    name: string;
    row: MenuStructure[];
    rows: MenuStructure[];
    type: string;
}

export interface MenuStructureRequest {
    mst_id: number;
    mst_mst_id: number;
    mst_order: number;
}

export interface NewPasswordRequest extends Serializable {
    email: string;
}

export interface NewsCommentModel {
    canDeleteComment: boolean;
    canEditComment: boolean;
    nwcComment: string;
    nwcCreateDate: Date;
    nwcId: number;
    nwcNwId: number;
    nwcNwcId: number;
    nwcUser: string;
    nwcUsrId: number;
    replyComments: NewsCommentModel[];
}

export interface NewsEditModel {
    attachment: SprFile[];
    comments: NewsCommentModel[];
    nwDateFrom: Date;
    nwDateTo: Date;
    nwId: number;
    nwLang: string;
    nwPublished: Date;
    nwSummary: string;
    nwText: string;
    nwTitle: string;
    nwType: string;
}

export interface NotificationRecipient {
    name: string;
    orgId: number;
    rolId: number;
    usrId: number;
}

export interface NotificationRequest {
    ntfMessage: string;
    ntfReference: number;
    ntfTitle: string;
    recipients: NotificationRecipient[];
}

export interface NtfRecipientsSearchReq {
    foreignKeyParams: ForeignKeyParams;
    selectedIds: number[];
}

export interface NtisAddrSearchRequest {
    flatNo: string;
    houseNo: string;
    latitude: number;
    longitude: number;
    municipalityCode: number;
    residenceCode: number;
    streetCode: number;
    uniqueNo?: string;
}

export interface NtisAddrSearchResult {
    ad_id: number;
    address: string;
    city: string;
    housing: string;
    latitude: number;
    longitude: number;
    municipality: string;
    street: string;
    wtf_id?: number;
}

export interface NtisAddressSuggestion {
    address: string;
    id: number;
    lksX: number;
    lksY: number;
}

export interface NtisAdrAddressesDAO extends NtisAdrAddressesDAOGen {
}

export interface NtisAdrAddressesDAOGen extends SprBaseDAO {
    ad_address: string;
    ad_address_search: string;
    ad_ads_id: number;
    ad_apl_id: number;
    ad_coordinate_latitude: number;
    ad_coordinate_longitude: number;
    ad_date_from: Date;
    ad_date_to: Date;
    ad_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisAdrMappingsDAO extends NtisAdrMappingsDAOGen {
}

export interface NtisAdrMappingsDAOGen extends SprBaseDAO {
    am_address_type: string;
    am_id: number;
    am_municipality_code: string;
    am_org_id: number;
    am_provided_addres_name: string;
    am_re_id: number;
    am_sen_id: number;
    am_str_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisAdrPatLrsDAO extends NtisAdrPatLrsDAOGen {
}

export interface NtisAdrPatLrsDAOGen extends SprBaseDAO {
    apl_ads_id: number;
    apl_aob_code: number;
    apl_date_from: Date;
    apl_date_to: Date;
    apl_id: number;
    apl_municipality_code: number;
    apl_pat_code: number;
    apl_pat_nr: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisAdrResidencesDAO extends NtisAdrResidencesDAOGen {
}

export interface NtisAdrResidencesDAOGen extends SprBaseDAO {
    re_date_from: Date;
    re_date_to: Date;
    re_id: number;
    re_municipality_code: number;
    re_name: string;
    re_name_k: string;
    re_recidence_code: number;
    re_sen_code: number;
    re_type: string;
    re_type_abbreviation: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisAdrSeniunijosDAO extends NtisAdrSeniunijosDAOGen {
}

export interface NtisAdrSeniunijosDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    sen_code: string;
    sen_date_from: Date;
    sen_date_to: Date;
    sen_id: number;
    sen_municipality_code: string;
    sen_name: string;
}

export interface NtisAdrStatsDAO extends NtisAdrStatsDAOGen {
}

export interface NtisAdrStatsDAOGen extends SprBaseDAO {
    ads_aob_code: number;
    ads_coordinate_latitude: number;
    ads_coordinate_longitude: number;
    ads_date_from: Date;
    ads_date_to: Date;
    ads_housing_nr: string;
    ads_id: number;
    ads_municipality_code: number;
    ads_nr: string;
    ads_post_code: string;
    ads_re_id: number;
    ads_residence_code: number;
    ads_str_id: number;
    ads_street_code: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisAdrStreetsDAO extends NtisAdrStreetsDAOGen {
}

export interface NtisAdrStreetsDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    str_date_from: Date;
    str_date_to: Date;
    str_id: number;
    str_name: string;
    str_re_id: number;
    str_residence_code: number;
    str_street_code: number;
    str_type: string;
    str_type_abbreviation: string;
}

export interface NtisAggloMapTableGeom {
    geom: string;
    id: number;
}

export interface NtisAggloMapTableItem {
    area: number;
    docDate: string;
    docName: string;
    docNo: string;
    id: number;
    maxX: number;
    maxY: number;
    minX: number;
    minY: number;
    municipalityCode: number;
    name: string;
    populationDensity: number;
    populationEquivalent: number;
}

export interface NtisAggloRejectRequest {
    aggId: number;
    description: string;
    file: SprFile;
}

export interface NtisAgglomerationGeomsDAO extends NtisAgglomerationGeomsDAOGen {
}

export interface NtisAgglomerationGeomsDAOGen extends SprBaseDAO {
    ag_agg_id: number;
    ag_area: number;
    ag_av_id: number;
    ag_density: number;
    ag_id: number;
    ag_municipality: number;
    ag_name: string;
    ag_obj_date: Date;
    ag_population: number;
    ag_status: string;
    ag_uuid: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisAgglomerationNotesDAO extends NtisAgglomerationNotesDAOGen {
}

export interface NtisAgglomerationNotesDAOGen extends SprBaseDAO {
    an_av_id: number;
    an_created: Date;
    an_description: string;
    an_id: number;
    an_type: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisAgglomerationVersionsDAO extends NtisAgglomerationVersionsDAOGen {
}

export interface NtisAgglomerationVersionsDAOGen extends SprBaseDAO {
    av_admin_review: string;
    av_admin_review_date: Date;
    av_admin_review_fil_id: number;
    av_admin_review_per_id: number;
    av_agg_id: number;
    av_created: Date;
    av_fil_id: number;
    av_id: number;
    av_per_id: number;
    av_status: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisAgglomerationsDAO extends NtisAgglomerationsDAOGen {
}

export interface NtisAgglomerationsDAOGen extends SprBaseDAO {
    agg_agglo_type: string;
    agg_confirmed_date: Date;
    agg_confirmed_document_number: string;
    agg_created: Date;
    agg_id: number;
    agg_municipality: number;
    agg_state: string;
    agg_state_date: Date;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisAglomeracijosDAO extends NtisAglomeracijosDAOGen {
}

export interface NtisAglomeracijosDAOGen extends SprBaseDAO {
    a_dok_data: Date;
    a_dok_nr: string;
    a_dok_pav: string;
    a_plotas: number;
    a_ter_id: number;
    a_uuid: string;
    ge: number;
    geom: string;
    gyv_tankis: number;
    ogc_fid: number;
    pav: string;
    sav_kodas: number;
}

export interface NtisBuildingAgreementsDAO extends NtisBuildingAgreementsDAOGen {
}

export interface NtisBuildingAgreementsDAOGen extends SprBaseDAO {
    ba_bn_id: number;
    ba_created: Date;
    ba_fil_id: number;
    ba_id: number;
    ba_manual_network_con_date: Date;
    ba_manual_org_id: number;
    ba_network_connection_date: Date;
    ba_network_disconnection_date: Date;
    ba_org_id: number;
    ba_rejection_reason: string;
    ba_source: string;
    ba_state: string;
    ba_wastewater_treatment: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisBuildingNtrOwnersDAO extends NtisBuildingNtrOwnersDAOGen {
}

export interface NtisBuildingNtrOwnersDAOGen extends SprBaseDAO {
    bno_bn_id: number;
    bno_code: string;
    bno_id: number;
    bno_lastname: string;
    bno_name: string;
    bno_org_name: string;
    bno_type: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisBuildingNtrsDAO extends NtisBuildingNtrsDAOGen {
}

export interface NtisBuildingNtrsDAOGen extends SprBaseDAO {
    bn_ad_id: number;
    bn_adr_id: number;
    bn_aob_code: number;
    bn_construction_completion: number;
    bn_construction_end_year: number;
    bn_construction_start_year: number;
    bn_coordinate_latitude_adr: number;
    bn_coordinate_latitude_ntr: number;
    bn_coordinate_longitude_adr: number;
    bn_coordinate_longitude_ntr: number;
    bn_date_from: Date;
    bn_date_to: Date;
    bn_declr_type: string;
    bn_flat_nr: string;
    bn_house_nr: string;
    bn_housing_nr: string;
    bn_id: number;
    bn_live_count: number;
    bn_living_area: number;
    bn_municipality: string;
    bn_municipality_code: string;
    bn_obj_inv_code: string;
    bn_obj_inv_parent_code: string;
    bn_object_inv_date: Date;
    bn_object_name: string;
    bn_object_type: number;
    bn_pask_name: string;
    bn_pask_type: number;
    bn_place_code: string;
    bn_place_name: string;
    bn_reg_date: Date;
    bn_reg_nr: string;
    bn_sen_code: string;
    bn_sen_name: string;
    bn_status: string;
    bn_status_desc: string;
    bn_street: string;
    bn_street_code: string;
    bn_total_area: number;
    bn_useable_area: number;
    bn_wastewater_treatment: string;
    bn_water_supply: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisBuildingsMapTableItem {
    address: string;
    belongsToNtrNumber: string;
    info: string;
    isCentralized: boolean;
    maxX: number;
    maxY: number;
    minX: number;
    minY: number;
    ntrNumber: string;
    purpose: string;
    status: string;
}

export interface NtisCar {
    capacity: number;
    id: number;
    isInUse: boolean;
    model: string;
    regNo: string;
    tubeLength: number;
}

export interface NtisCarsDAO extends NtisCarsDAOGen {
    ntisCarModel: NtisCar;
}

export interface NtisCarsDAOGen extends SprBaseDAO {
    cr_capacity: number;
    cr_date_from: Date;
    cr_date_to: Date;
    cr_id: number;
    cr_model: string;
    cr_org_id: number;
    cr_reg_no: string;
    cr_tube_length: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisCheckWtfSelectionRequest {
    adId?: number;
    address?: string;
    lksX: number;
    lksY: number;
    wtfDistance?: number;
    wtfId?: number;
    wtfType?: string;
}

export interface NtisCheckWtfSelectionResponse {
    selected: boolean;
    wtfs: NtisCheckWtfSelectionResponseWtf[];
}

export interface NtisCheckWtfSelectionResponseWtf {
    adId: number;
    address: string;
    lksX: number;
    lksY: number;
    ownerType: string;
    ownerTypeCode: string;
    wtfId: number;
    wtfType: string;
    wtfTypeCode: string;
}

export interface NtisContractCommentsDAO extends NtisContractCommentsDAOGen {
}

export interface NtisContractCommentsDAOGen extends SprBaseDAO {
    cc_cot_id: number;
    cc_created: Date;
    cc_id: number;
    cc_message: string;
    cc_org_id: number;
    cc_per_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisContractEditModel extends NtisContractsDAO {
    attachment: SprFile;
    cot_comments: NtisContractRequestComment[];
    cot_services: NtisContractRequestService[];
    cot_state_meaning: string;
    org_id: number;
    org_name: string;
    per_name: string;
    sp_info: SprOrganizationsDAO;
    wtf_info: NtisWtfInfo;
}

export interface NtisContractRequestComment {
    author: string;
    cc_id?: number;
    contractId?: number;
    text: string;
    time: string;
}

export interface NtisContractRequestService {
    cot_id?: number;
    cs_id?: number;
    description: string;
    name: string;
    price?: number;
    srv_id?: number;
    type: string;
}

export interface NtisContractServicesDAO extends NtisContractServicesDAOGen {
}

export interface NtisContractServicesDAOGen extends SprBaseDAO {
    cs_cot_id: number;
    cs_id: number;
    cs_price: number;
    cs_srv_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisContractSignReq {
    url: string;
}

export interface NtisContractUploadRequest {
    contractId: number;
    filKey: string;
    spOrgId: number;
    wtfId: number;
}

export interface NtisContractsDAO extends NtisContractsDAOGen {
    jarOrgId: number;
    servicesJson: string;
    signingOrgId: number;
    signingPerId: number;
}

export interface NtisContractsDAOGen extends SprBaseDAO {
    cot_client_email: string;
    cot_client_phone_no: string;
    cot_code: string;
    cot_created: Date;
    cot_created_in_ntis_portal: string;
    cot_fil_id: number;
    cot_from_date: Date;
    cot_id: number;
    cot_org_id: number;
    cot_per_id: number;
    cot_project_created: Date;
    cot_rejection_date: Date;
    cot_rejection_reason: string;
    cot_sign_1_fil_id: number;
    cot_sign_2_fil_id: number;
    cot_state: string;
    cot_to_date: Date;
    cot_wtf_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisCountyMunicipality {
    children: NtisCountyMunicipality[];
    code: string;
    description: string;
    name: string;
}

export interface NtisCwFileDataDAO extends NtisCwFileDataDAOGen {
}

export interface NtisCwFileDataDAOGen extends SprBaseDAO {
    cwfd_atjungimo_data: string;
    cwfd_buto_nr: string;
    cwfd_cwf_id: number;
    cwfd_eil_nr: number;
    cwfd_gatve: string;
    cwfd_gatves_kodas: string;
    cwfd_gyv_vieta: string;
    cwfd_gyv_vietos_kodas: string;
    cwfd_id: number;
    cwfd_korpuso_nr: string;
    cwfd_nuot_salinimo_budas: string;
    cwfd_pastato_adr_kodas: string;
    cwfd_pastato_kodas: string;
    cwfd_pastato_nr: string;
    cwfd_patalpos_kodas: string;
    cwfd_prijungimo_data: string;
    cwfd_savivaldybe: string;
    cwfd_savivaldybes_kodas: string;
    cwfd_seniunija: string;
    cwfd_seniunijos_kodas: string;
    cwfd_statinio_vald_kodas: string;
    cwfd_status: string;
    cwfd_vandentvarkos_im_kod: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisCwFileDataErrsDAO extends NtisCwFileDataErrsDAOGen {
}

export interface NtisCwFileDataErrsDAOGen extends SprBaseDAO {
    cwfde_column_name: string;
    cwfde_column_nr: number;
    cwfde_column_value: string;
    cwfde_cwf_id: number;
    cwfde_cwfd_id: number;
    cwfde_id: number;
    cwfde_level: string;
    cwfde_msg_code: string;
    cwfde_msg_text: string;
    cwfde_rec_nr: number;
    cwfde_type: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisCwFilesDAO extends NtisCwFilesDAOGen {
}

export interface NtisCwFilesDAOGen extends SprBaseDAO {
    cwf_fil_id: number;
    cwf_id: number;
    cwf_import_date: Date;
    cwf_org_id: number;
    cwf_status: string;
    cwf_status_date: Date;
    cwf_usr_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisDeliveryDetailsForOrder {
    org_name: string;
    wd_delivery_date: Date;
    wd_id: number;
    wd_rejection_reason: string;
    wd_state: string;
    wd_state_meaning: string;
    wto_name: string;
}

export interface NtisDeliveryFacilitiesDAO extends NtisDeliveryFacilitiesDAOGen {
}

export interface NtisDeliveryFacilitiesDAOGen extends SprBaseDAO {
    df_delivery_sludge_quentity: number;
    df_id: number;
    df_ord_id: number;
    df_removed_date: Date;
    df_wd_id: number;
    df_wtf_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisDischargeWastewaterDAO extends NtisDischargeWastewaterDAOGen {
}

export interface NtisDischargeWastewaterDAOGen extends SprBaseDAO {
    dw_ad_id: number;
    dw_coordinate_latitude: number;
    dw_coordinate_longitude: number;
    dw_id: number;
    dw_type: string;
    dw_wtf_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisFacilityFilesDAO extends NtisFacilityFilesDAOGen {
}

export interface NtisFacilityFilesDAOGen extends SprBaseDAO {
    ff_fil_id: number;
    ff_id: number;
    ff_wtf_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisFacilityLocationsDAO extends NtisFacilityLocationsDAOGen {
}

export interface NtisFacilityLocationsDAOGen extends SprBaseDAO {
    fl_ad_id: number;
    fl_coordinate_latitude: number;
    fl_coordinate_longitude: number;
    fl_id: number;
    fl_wtf_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisFacilityManagerEditModel {
    fo_date_from: Date;
    fo_date_to: Date;
    fo_id: number;
    fo_wtf_id: number;
    per_code: string;
    per_name: string;
    per_surname: string;
}

export interface NtisFacilityModel {
    rfc_code: string;
    rfc_id: string;
    rfc_meaning: string;
}

export interface NtisFacilityModelDAO extends NtisFacilityModelDAOGen {
}

export interface NtisFacilityModelDAOGen extends SprBaseDAO {
    fam_bds: number;
    fam_chds: number;
    fam_description: string;
    fam_fil_id: number;
    fam_float_material: number;
    fam_id: number;
    fam_manufacturer: string;
    fam_model: string;
    fam_nitrogen: number;
    fam_phosphor: number;
    fam_pop_equivalent: number;
    fam_rfc_id: number;
    fam_tech_pass: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisFacilityModelEditModel {
    facilityModel: SprRefCodesDAO;
    facilityModelAdditionalDetails: NtisFacilityModelDAO;
    passport: SprFile;
}

export interface NtisFacilityOwnersDAO extends NtisFacilityOwnersDAOGen {
}

export interface NtisFacilityOwnersDAOGen extends SprBaseDAO {
    fo_date_from: Date;
    fo_date_to: Date;
    fo_fo_id: number;
    fo_id: number;
    fo_managing_org_id: number;
    fo_managing_per_id: number;
    fo_org_id: number;
    fo_owner_type: string;
    fo_per_id: number;
    fo_selected: string;
    fo_so_id: number;
    fo_wtf_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisFacilityUpdateAgreementDAO extends NtisFacilityUpdateAgreementDAOGen {
}

export interface NtisFacilityUpdateAgreementDAOGen extends SprBaseDAO {
    fua_bn_id: number;
    fua_cancellation_reason: string;
    fua_confirmed_usr_id: number;
    fua_created: Date;
    fua_fil_id: number;
    fua_id: number;
    fua_manufecturer: string;
    fua_model: string;
    fua_network_connection_date: Date;
    fua_org_id: number;
    fua_per_id: number;
    fua_req_org_id: number;
    fua_so_id: number;
    fua_state: string;
    fua_type: string;
    fua_wtf_id: number;
    fua_wtf_new_info_json: string;
    fua_wtf_object_info_json: string;
    fua_wtf_old_info_json: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisFacilityUpdateLogDAO extends NtisFacilityUpdateLogDAOGen {
}

export interface NtisFacilityUpdateLogDAOGen extends SprBaseDAO {
    ful_created: Date;
    ful_id: number;
    ful_operation: string;
    ful_org_id: number;
    ful_usr_id: number;
    ful_wtf_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisFavoriteSrvProvidersDAO extends NtisFavoriteSrvProvidersDAOGen {
}

export interface NtisFavoriteSrvProvidersDAOGen extends SprBaseDAO {
    fsp_id: number;
    fsp_org_id: number;
    fsp_org_id_favorite: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisFavoriteWastTreatOrgDAO extends NtisFavoriteWastTreatOrgDAOGen {
}

export interface NtisFavoriteWastTreatOrgDAOGen extends SprBaseDAO {
    fwto_id: number;
    fwto_org_id: number;
    fwto_wto_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisINTSDashboardContract {
    cot_created: Date;
    cot_id: number;
    cot_srv_provider: string;
    cot_state: string;
    cot_wtf_id: number;
}

export interface NtisINTSDashboardLab {
    compliance_norms: string;
    lab_name: string;
    ord_created: Date;
    ord_id: number;
    ord_state: string;
    ord_wtf_id: number;
    rev_comment: string;
    rev_id: number;
    rev_score: number;
    wtf_id: number;
}

export interface NtisINTSDashboardModel {
    availableActions: string[];
    contracts: NtisINTSDashboardContract[];
    disposalOrders: NtisINTSDashboardOrder[];
    facilities: NtisINTSDashboardWastewater[];
    labs: NtisINTSDashboardLab[];
    techOrders: NtisINTSDashboardOrder[];
}

export interface NtisINTSDashboardOrder {
    ord_date: Date;
    ord_id: number;
    ord_state: string;
    rev_comment: string;
    rev_id: number;
    rev_score: number;
    srv_id: number;
    srv_provider: string;
    wtf_id: number;
}

export interface NtisINTSDashboardWastewater {
    fo_owner_type: string;
    fo_selected: string;
    fua_ID: string;
    fua_id: string;
    ful_id: number;
    latitude: number;
    longitude: number;
    wtf_address: string;
    wtf_fua_state: string;
    wtf_id: number;
    wtf_served_objects: string;
    wtf_state: string;
    wtf_type: string;
}

export interface NtisInstitutionEditModel {
    municipality: number;
    org_code: string;
    org_id: number;
    org_name: string;
    org_type: string;
    ou_id: number;
    per_email: string;
    per_id: number;
    per_name: string;
    per_phone_number: string;
    per_surname: string;
    sendInvitation: string;
    usr_id: number;
    usr_username: string;
}

export interface NtisIsenseAuthModel {
    host: string;
    token: string;
}

export interface NtisJobEditModel {
    jobDefinitionsDAO: SprJobDefinitionsDAO;
    numberOfAttempts: number;
    periodAfterAttempt: number;
}

export interface NtisMapBuildPointDetails {
    addresses: string[];
    coordinates: number[];
    facilities: NtisMapFacilityDetails[];
    poId: number;
}

export interface NtisMapCentDetails {
    address?: string;
    municipality?: string;
    ntrNumber?: string;
    placeName?: string;
    street?: string;
    x?: number;
    y?: number;
}

export interface NtisMapClickedPoint {
    description?: string;
    id: number;
    layer: string;
    name: string;
}

export interface NtisMapDisposalDetails {
    address?: string;
    disposalDate?: string;
    id?: number;
    link?: string;
    stateName?: string;
    wtfId?: number;
    x?: number;
    y?: number;
}

export interface NtisMapFacilityDetails {
    address?: string;
    capacity?: string;
    checkoutDate?: string;
    dischargeCoordinates?: number[];
    dischargeCoordinatesText?: string;
    dischargeType?: string;
    distance?: string;
    facilityCoordinates: number[];
    installationDate?: string;
    manufacturer?: string;
    manufacturerDescription?: string;
    model?: string;
    servedObjectAddresses?: string[];
    servedObjects?: NtisMapPoint[];
    state?: string;
    stateCode?: string;
    technicalPassport?: string;
    type?: string;
    typeCode?: string;
    wtfId?: number;
    x?: number;
    y?: number;
}

export interface NtisMapPoint {
    coordinates: number[];
    id: number;
}

export interface NtisMapResearchDetails {
    aComplianceName?: string;
    aDate?: string;
    aNorm?: number;
    aValue?: number;
    address?: string;
    bComplianceName?: string;
    bDate?: string;
    bNorm?: number;
    bValue?: number;
    cComplianceName?: string;
    cDate?: string;
    cNorm?: number;
    cValue?: number;
    dComplianceName?: string;
    dDate?: string;
    dNorm?: number;
    dValue?: number;
    id?: number;
    link?: string;
    wtfId?: number;
    x?: number;
    y?: number;
}

export interface NtisMapTableResult<T> {
    filteredItems: number;
    items: T[];
    totalItems: number;
}

export interface NtisMessagesDAO extends NtisMessagesDAOGen {
}

export interface NtisMessagesDAOGen extends SprBaseDAO {
    mes_cot_id: number;
    mes_created: Date;
    mes_description: string;
    mes_id: number;
    mes_priority: string;
    mes_subject: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisMstAdditionalText {
    mst_id: string;
    mst_tooltip: string;
}

export interface NtisMunicipalitiesDAO extends NtisMunicipalitiesDAOGen {
}

export interface NtisMunicipalitiesDAOGen extends SprBaseDAO {
    mp_code: string;
    mp_date_from: Date;
    mp_date_to: Date;
    mp_id: number;
    mp_name: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisMunicipalitiesRequest {
    mn_date_from: Date;
    mn_date_to: Date;
    mn_id: number;
    mn_municipality: string;
    mn_srv_id: number;
    rfc_code: string;
    rfc_meaning: string;
    selected: boolean;
}

export interface NtisNewContractRequest {
    srvProvId: number;
    wtfId?: number;
}

export interface NtisNewContractRequestInfo {
    applicantEmail: string;
    applicantPhone: string;
    availableServices: NtisContractRequestService[];
    orgEmail: string;
    orgId: number;
    orgName: string;
    orgPhone: string;
    wtfInfo: NtisWtfInfo;
}

export interface NtisNewOrderDetails {
    ord_additional_description: string;
    ord_completion_request_date: Date;
    ord_email: string;
    ord_name: string;
    ord_phone_number: string;
}

export interface NtisNewServiceRequest {
    org_address: string;
    org_code: string;
    org_email: string;
    org_house_number: string;
    org_installation_from: Date;
    org_name: string;
    org_phone: string;
    org_region: string;
    org_type: string;
    org_website: string;
    per_date_of_birth: string;
    per_name: string;
    per_surname: string;
    usr_id: number;
}

export interface NtisNewsEditModel extends NewsEditModel {
    isPublic: string;
    isTemplate: string;
    pageTemplateType: string;
    saveAsNewTemplate?: boolean;
}

export interface NtisNotificationContactsModel {
    clientEmail: string;
    clientName: string;
    clientPhone: string;
    orgEmail: string;
    orgName: string;
    orgPhone: string;
}

export interface NtisNotificationViewModel {
    body: string;
    contactInfo: NtisNotificationContactsModel;
    date: Date;
    msgSubject: string;
    refId: number;
    refType: string;
    subject: string;
}

export interface NtisOrdImportFile {
    fil_content_type: string;
    fil_key: string;
    fil_name: string;
    fil_size: number;
    fil_status: string;
    fil_status_date: Date;
    orf_id: number;
    orf_import_date: Date;
    orf_status: string;
    orf_status_date: Date;
    org_name: string;
    srv_type: string;
    total_errors: number;
    total_records: number;
}

export interface NtisOrderCarSelection {
    cr_id: number;
    cr_name: string;
}

export interface NtisOrderCompletedWorksDAO extends NtisOrderCompletedWorksDAOGen {
}

export interface NtisOrderCompletedWorksDAOGen extends SprBaseDAO {
    ocw_completed_date: Date;
    ocw_completed_works_description: string;
    ocw_cr_id: number;
    ocw_discharged_sludge_amount: number;
    ocw_fil_id: number;
    ocw_id: number;
    ocw_ord_id: number;
    ocw_res_person: string;
    ocw_rsr_person: string;
    ocw_smp_person: string;
    ocw_usr_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisOrderCompletedWorksRequest {
    ocw_completed_date: Date;
    ocw_completed_works_description: string;
    ocw_ord_id: number;
}

export interface NtisOrderDetails {
    car_name: string;
    ocw_completed_date: Date;
    ocw_completed_works_description: string;
    ocw_cr_id: number;
    ocw_discharged_sludge_amount: number;
    ocw_id: number;
    ord_additional_description: string;
    ord_completion_estimate_date: Date;
    ord_completion_request_date: Date;
    ord_created: Date;
    ord_cs_id?: number;
    ord_email: string;
    ord_id: number;
    ord_name: string;
    ord_org_id: number;
    ord_per_id: number;
    ord_phone_number: string;
    ord_planned_works_description: string;
    ord_prefered_date_from: Date;
    ord_prefered_date_to: Date;
    ord_rejection_date: Date;
    ord_rejection_reason: string;
    ord_srv_id: number;
    ord_state: string;
    ord_state_clsf: string;
    ord_usr_id: number;
    ord_wtf_id: number;
}

export interface NtisOrderFileDataDAO extends NtisOrderFileDataDAOGen {
}

export interface NtisOrderFileDataDAOGen extends SprBaseDAO {
    orfd_adresas: string;
    orfd_atlikimo_data: string;
    orfd_atlikti_darbai: string;
    orfd_azotas: string;
    orfd_buto_nr: string;
    orfd_cr_id: number;
    orfd_deguonis: string;
    orfd_eil_nr: number;
    orfd_fosforas: string;
    orfd_gatve: string;
    orfd_gatves_kodas: string;
    orfd_gyv_vieta: string;
    orfd_gyv_vietos_kodas: string;
    orfd_id: number;
    orfd_isvezimo_data: string;
    orfd_isveztas_kiekis: string;
    orfd_korpuso_nr: string;
    orfd_laboratorijos_komentaras: string;
    orfd_meginio_darbuotojas: string;
    orfd_meginio_data: string;
    orfd_orf_id: number;
    orfd_org_id: number;
    orfd_paslauga: string;
    orfd_paslaugos_kodas: string;
    orfd_pastaba_rezultatams: string;
    orfd_pastato_adr_kodas: string;
    orfd_pastato_kodas: string;
    orfd_pastato_nr: string;
    orfd_patalpos_kodas: string;
    orfd_savivaldybe: string;
    orfd_savivaldybes_kodas: string;
    orfd_seniunija: string;
    orfd_seniunijos_kodas: string;
    orfd_skendincios: string;
    orfd_transporto_priemone: string;
    orfd_tyrimo_darbuotojas: string;
    orfd_tyrimo_data: string;
    orfd_uzsakovas: string;
    orfd_uzsakovo_email: string;
    orfd_uzsakovo_komentaras: string;
    orfd_uzsakovo_tel: string;
    orfd_uzsakymo_data: string;
    orfd_uzsakymo_informacija: string;
    orfd_wtf_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisOrderFileDataErrsDAO extends NtisOrderFileDataErrsDAOGen {
}

export interface NtisOrderFileDataErrsDAOGen extends SprBaseDAO {
    orfde_column_name: string;
    orfde_column_nr: number;
    orfde_column_value: string;
    orfde_id: number;
    orfde_level: string;
    orfde_msg_code: string;
    orfde_msg_text: string;
    orfde_orf_id: number;
    orfde_orfd_id: number;
    orfde_rec_nr: number;
    orfde_type: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisOrderFilesDAO extends NtisOrderFilesDAOGen {
}

export interface NtisOrderFilesDAOGen extends SprBaseDAO {
    orf_fil_id: number;
    orf_id: number;
    orf_import_date: Date;
    orf_org_id: number;
    orf_srv_id: number;
    orf_status: string;
    orf_status_date: Date;
    orf_usr_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisOrdersDAO extends NtisOrdersDAOGen {
}

export interface NtisOrdersDAOGen extends SprBaseDAO {
    ord_additional_description: string;
    ord_completion_estimate_date: Date;
    ord_completion_request_date: Date;
    ord_compliance_norms: string;
    ord_created: Date;
    ord_created_in_ntis_portal: string;
    ord_cs_id: number;
    ord_email: string;
    ord_id: number;
    ord_org_id: number;
    ord_per_id: number;
    ord_phone_number: string;
    ord_planned_works_description: string;
    ord_prefered_date_from: Date;
    ord_prefered_date_to: Date;
    ord_rejection_date: Date;
    ord_rejection_reason: string;
    ord_removed_sewage_date: Date;
    ord_srv_id: number;
    ord_state: string;
    ord_type: string;
    ord_usr_id: number;
    ord_wtf_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisOrdersRequest {
    actionType?: string;
    ord_additional_description?: string;
    ord_cs_id?: number;
    ord_id: number;
    ord_org_id?: number;
    ord_planned_date?: Date;
    ord_preferred_order_date?: Date;
    ord_rejection_reason?: string;
    ord_srv_id?: number;
    ord_state?: string;
    ord_wtf_id?: number;
}

export interface NtisPriorityFacilities {
    org_id: number;
    wto_id: number[];
}

export interface NtisRejectedAggloVersion {
    avId: number;
    description: string;
    file: SprFile;
    notes: string[];
    notesJson: string;
    person: string;
    rejectDate: string;
    status: string;
    statusCode: string;
    uploadDate: string;
}

export interface NtisResearchDAO extends NtisResearchDAOGen {
}

export interface NtisResearchDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    res_created: Date;
    res_description: string;
    res_id: number;
    res_ord_id: number;
    res_research_date: Date;
    res_reserch_type: string;
    res_sample_date: Date;
    res_value: number;
}

export interface NtisResearchNormDAO extends NtisResearchNormDAOGen {
}

export interface NtisResearchNormDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    rn_created: Date;
    rn_date_from: Date;
    rn_date_to: Date;
    rn_facility_installation_date: string;
    rn_id: number;
    rn_newest: string;
    rn_research_norm: number;
    rn_research_type: string;
    rn_usr_id: number;
}

export interface NtisResearchNormEditModel {
    rn_date_from: Date;
    rn_date_to: Date;
    rn_facility_installation_date: string;
    rn_id: number;
    rn_research_norm: number;
    rn_research_type: string;
}

export interface NtisResearchOrderDAO extends NtisResearchOrderDAOGen {
}

export interface NtisResearchOrderDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    ro_additional_description: string;
    ro_compliance_norms: string;
    ro_created: string;
    ro_created_in_ntis_portal: string;
    ro_date_from: string;
    ro_date_to: string;
    ro_email: string;
    ro_fil_id: number;
    ro_id: number;
    ro_org_id: number;
    ro_phone_number: string;
    ro_rejection_reason: string;
    ro_state: string;
    ro_wtf_id: number;
}

export interface NtisResearchOrderModel {
    comment: string;
    completionEstimate: Date;
    createdInNtis: string;
    facility: NtisWtfInfo;
    ocwId: number;
    ordCreated: Date;
    ordId: number;
    ordererEmail: string;
    ordererName: string;
    ordererPhone: string;
    orgEmail: string;
    orgName: string;
    orgPhone: string;
    rejectionDate: Date;
    rejectionReason: string;
    requestedDateFrom: Date;
    requestedDateTo: Date;
    researchComments: string;
    researchDate: Date;
    researchPerson: string;
    responsiblePerson: string;
    results: ResearchCriteriaResultsModel[];
    resultsDate: Date;
    resultsFile: SprFile;
    resultsPerson: string;
    revComment: string;
    revId: number;
    revScore: number;
    rn_id: number;
    sampleDate: Date;
    samplePerson: string;
    selectedCriteria: ResearchRequestedCriteriaModel[];
    status: string;
    statusClsf: string;
    wtfId: number;
}

export interface NtisResearchOrderServiceDAO extends NtisResearchOrderServiceDAOGen {
}

export interface NtisResearchOrderServiceDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    ros_id: number;
    ros_ro_id: number;
    ros_srv_id: number;
}

export interface NtisResearchesDAO extends NtisResearchesDAOGen {
}

export interface NtisResearchesDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    res_created: Date;
    res_description: string;
    res_id: number;
    res_ord_id: number;
    res_research_date: Date;
    res_reserch_type: string;
    res_rn_id: number;
    res_sample_date: Date;
    res_value: number;
}

export interface NtisReviewCreationModel {
    ordDate: Date;
    ordId: string;
    orgName: string;
    reviewInfo: NtisReviewsDAO;
    srvType: string;
}

export interface NtisReviewsDAO extends NtisReviewsDAOGen {
}

export interface NtisReviewsDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    rev_admin_read: string;
    rev_comment: string;
    rev_completed_date: Date;
    rev_id: number;
    rev_ord_id: number;
    rev_pasl_org_id: number;
    rev_receiver_read: string;
    rev_score: number;
    rev_usr_id: number;
    rev_vand_org_id: number;
    rev_wd_id: number;
}

export interface NtisSPSettingsServiceInfo {
    is_active: string;
    service_type: string;
}

export interface NtisSelectedFacilitiesDAO extends NtisSelectedFacilitiesDAOGen {
}

export interface NtisSelectedFacilitiesDAOGen extends SprBaseDAO {
    fs_id: number;
    fs_org_id: number;
    fs_usr_id: number;
    fs_wtf_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisServedBuildingUpdateModel {
    attachments: SprFile;
    ba_network_connection_date: Date;
    ba_org_id: number;
    ba_so_address: string;
    ba_so_id: number;
    ba_state: string;
    ba_update_by: string;
    fua_id: number;
}

export interface NtisServedObjectsDAO extends NtisServedObjectsDAOGen {
}

export interface NtisServedObjectsDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    so_ad_id: number;
    so_bn_id: number;
    so_coordinate_latitude: number;
    so_coordinate_longitude: number;
    so_date_from: Date;
    so_date_to: Date;
    so_id: number;
    so_wtf_id: number;
}

export interface NtisServedObjectsVersionDAO extends NtisServedObjectsVersionDAOGen {
}

export interface NtisServedObjectsVersionDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    sov_ad_id: number;
    sov_bn_id: number;
    sov_coordinate_latitude: number;
    sov_coordinate_longitude: number;
    sov_fua_id: number;
    sov_geom: string;
    sov_id: number;
    sov_wtf_id: number;
}

export interface NtisServiceDescription {
    sri_id: number;
    srv_available_in_ntis_portal: string;
    srv_completion_in_days_from: number;
    srv_completion_in_days_to: number;
    srv_contract_available: string;
    srv_date_from: Date;
    srv_date_to: Date;
    srv_description: string;
    srv_email: string;
    srv_fil_id: number;
    srv_id: number;
    srv_lab_instr_fil_id: number;
    srv_lithuanian_level: string;
    srv_name: string;
    srv_org_id: number;
    srv_org_name: string;
    srv_phone_no: string;
    srv_price_from: number;
    srv_price_to: number;
    srv_type: string;
}

export interface NtisServiceDescriptionDetails {
    cs_id: number;
    srv_address: string;
    srv_completion_in_days_from: number;
    srv_description: string;
    srv_email: string;
    srv_id: number;
    srv_name: string;
    srv_org_id: number;
    srv_org_name: string;
    srv_phone_no: string;
    srv_price_from: number;
    srv_price_to: number;
    srv_type: string;
}

export interface NtisServiceDescriptionEditModel {
    contractFile: SprFile;
    labInstructions: SprFile;
    municipalities: NtisMunicipalitiesRequest[];
    ntisServiceDescription: NtisServiceDescription;
}

export interface NtisServiceDetails {
    availableInNtisPortal: boolean;
    completionInDays: string;
    completionInDaysTo?: string;
    contractAvailable: boolean;
    description: string;
    email: string;
    filId: number;
    file: SprFile;
    instrFilId: number;
    labInstructions: SprFile;
    lithuanianLevel: boolean;
    municipalities: string[];
    orgId: number;
    orgName: string;
    phone: string;
    priceFrom: string;
    priceTo: string;
    serviceName: string;
    srvId: number;
    srv_type: string;
}

export interface NtisServiceManagementItem {
    availableInNtisPortal: boolean;
    contractAvailable: boolean;
    isConfirmed: boolean;
    lithuanianLevel: boolean;
    serviceName: string;
    serviceType: string;
    srId: number;
    sriId: number;
    srvId: number;
    status: string;
    statusName: string;
}

export interface NtisServiceMunicipalitiesDAO extends NtisServiceMunicipalitiesDAOGen {
}

export interface NtisServiceMunicipalitiesDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    smn_date_from: Date;
    smn_date_to: Date;
    smn_id: number;
    smn_municipality: string;
    smn_srv_id: number;
}

export interface NtisServiceOrderRequest {
    carSelection: NtisOrderCarSelection[];
    deliveryDetails: NtisDeliveryDetailsForOrder[];
    orderDetails: NtisOrderDetails;
    researchFile: SprFile;
    revComment: string;
    revId: number;
    revScore: number;
    selectedResearchTypes: string[];
    serviceDescription: NtisServiceDescriptionDetails;
    wastewaterFacility: NtisWtfInfo;
}

export interface NtisServiceProviderContacts {
    email: string;
    emailNotifications: boolean;
    orgId: number;
    phoneNumber: string;
    website: string;
}

export interface NtisServiceProviderRejectionModel {
    org_id: number;
    org_rejection_reason: string;
}

export interface NtisServiceProviderSettingsInfo {
    address: string;
    code: string;
    contacts: NtisServiceProviderContacts;
    deregisteredFrom: string;
    deregisteredReason: string;
    employeesCount: number;
    facilityInstallerFrom: string;
    favWaterManagers: string[];
    manager: string;
    name: string;
    orgType: string;
    registeredFrom: string;
    services: NtisSPSettingsServiceInfo[];
    vehiclesCount: number;
    wtoCount: number;
}

export interface NtisServiceReqDetails {
    attachments?: SprFile[];
    org_address: string;
    org_code: string;
    org_house_number: string;
    org_name: string;
    org_region: string;
    org_type: string;
    registered_services: string;
    reqItemsDAO?: NtisServiceReqItemsDAO[];
    sr_data_is_correct: string;
    sr_email: string;
    sr_email_verified: string;
    sr_homepage: string;
    sr_id: number;
    sr_org_id?: number;
    sr_phone: string;
    sr_registration_date: Date;
    sr_removal_reason: string;
    sr_resp_person_description: string;
    sr_rules_accepted: string;
    sr_status: string;
    sr_status_date: Date;
    sr_status_meaning: string;
    sr_type: string;
    sr_usr_id?: number;
}

export interface NtisServiceReqFilesDAO extends NtisServiceReqFilesDAOGen {
}

export interface NtisServiceReqFilesDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    srf_fil_id: number;
    srf_id: number;
    srf_sr_id: number;
}

export interface NtisServiceReqItemsDAO extends NtisServiceReqItemsDAOGen {
}

export interface NtisServiceReqItemsDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    sri_id: number;
    sri_registration_date: Date;
    sri_removal_date: Date;
    sri_service_type: string;
    sri_sr_id: number;
    sri_srv_id: number;
    sri_status: string;
    sri_status_date: Date;
}

export interface NtisServiceReqStatusLogsDAO extends NtisServiceReqStatusLogsDAOGen {
}

export interface NtisServiceReqStatusLogsDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    srs_id: number;
    srs_sr_id: number;
    srs_sri_id: number;
    srs_status: string;
    srs_status_date: Date;
}

export interface NtisServiceRequestsDAO extends NtisServiceRequestsDAOGen {
}

export interface NtisServiceRequestsDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    sr_data_is_correct: string;
    sr_email: string;
    sr_email_verified: string;
    sr_homepage: string;
    sr_id: number;
    sr_org_id: number;
    sr_per_id: number;
    sr_phone: string;
    sr_reg_no: string;
    sr_registration_date: Date;
    sr_removal_date: Date;
    sr_removal_reason: string;
    sr_resp_person_description: string;
    sr_rules_accepted: string;
    sr_status: string;
    sr_status_date: Date;
    sr_type: string;
    sr_usr_id: number;
}

export interface NtisServiceSearchRequest {
    address: string;
    carCapacity: number;
    distanceToObject: number;
    equipmentType: string;
    lang: string;
    lksX: number;
    lksY: number;
    priceClause?: string;
    ratingClause?: string;
    services: string[];
    wtfId?: number;
}

export interface NtisServiceSearchResult {
    hasCar: string;
    orgId: number;
    orgName: string;
    score: number;
    services: NtisServiceSearchResultService[];
}

export interface NtisServiceSearchResultService {
    completionInDays: number;
    completionInDaysTo?: number;
    contractAvailable: boolean;
    description: string;
    email: string;
    id: number;
    name: string;
    phone: string;
    priceFrom: number;
    priceTo: number;
    type: string;
}

export interface NtisServicesDAO extends NtisServicesDAOGen {
}

export interface NtisServicesDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    srv_available_in_ntis_portal: string;
    srv_completion_in_days_from: number;
    srv_completion_in_days_to: number;
    srv_contract_available: string;
    srv_date_from: Date;
    srv_date_to: Date;
    srv_description: string;
    srv_email: string;
    srv_fil_id: number;
    srv_id: number;
    srv_lab_instr_fil_id: number;
    srv_lithuanian_level: string;
    srv_org_id: number;
    srv_phone_no: string;
    srv_price_from: number;
    srv_price_to: number;
    srv_type: string;
}

export interface NtisSewageOriginFacility {
    df_id: string;
    name: string;
    ocw_cr_id: number;
    ocw_discharged_sludge_amount: string;
    ord_id: string;
    ord_removed_sewage_date: Date;
    type: string;
    wtf_id: number;
    wtf_type: string;
}

export interface NtisSludgeDeliveryDetails {
    cr_reg_no: string;
    org_code: string;
    org_email: string;
    org_name: string;
    org_phone: string;
    originFacilities: NtisSewageOriginFacility[];
    rec_create_timestamp: string;
    usedFacilities: NtisUsedSewageFacility[];
    wd_accepted_sewage_quantity: number;
    wd_delivered_quantity: number;
    wd_delivered_wastewater_description: string;
    wd_delivery_date: string;
    wd_description: string;
    wd_id: number;
    wd_rejection_reason: string;
    wd_sewage_type: string;
    wd_state: string;
    wd_state_clsf: string;
    wd_used_sludge_quantity: number;
    wto_address: string;
    wto_name: string;
}

export interface NtisSubmitAggloData {
    aggId?: number;
    approvalDate: Date;
    approvalDocNo: string;
    dataDocument: SprFile;
}

export interface NtisSubmitContractRequestInfo {
    applicantEmail: string;
    applicantPhone: string;
    comments: string[];
    endDate: string;
    orgId: number;
    services: string[];
    startDate: string;
    wtfId: number;
}

export interface NtisSubmittedAggloData {
    confirmDate: string;
    confirmDocNo: string;
    geoms: NtisSubmittedAggloDataGeom[];
    id: number;
    lastCheckReport: string[];
    municipality: string;
    status: string;
    statusCode: string;
    versions: NtisSubmittedAggloDataVersion[];
}

export interface NtisSubmittedAggloDataGeom {
    geom: string;
    id: number;
    name: string;
}

export interface NtisSubmittedAggloDataVersion {
    adminReviewDate: string;
    adminReviewPerson: string;
    createdDate: string;
    extent: string;
    file: SprFile;
    id: number;
    person: string;
    status: string;
    statusCode: string;
}

export interface NtisSystemWorksDAO extends NtisSystemWorksDAOGen {
}

export interface NtisSystemWorksDAOGen extends SprBaseDAO {
    nsw_additional_information: string;
    nsw_id: number;
    nsw_is_active: string;
    nsw_notification_sent: string;
    nsw_show_date_from: Date;
    nsw_show_date_to: Date;
    nsw_works_date_from: Date;
    nsw_works_date_to: Date;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface NtisSystemWorksEditModel {
    additionalInformation: string;
    endDate: Date;
    isActive: string;
    nswId: number;
    startDate: Date;
    worksDateFrom: Date;
    worksDateTo: Date;
}

export interface NtisSystemWorksInfo {
    additionalInformation: string;
    endDate: Date;
    startDate: Date;
    worksDateFrom: string;
    worksDateTo: string;
}

export interface NtisUsedSewageFacility {
    capacity: string;
    typeClsf: string;
    us_id: string;
    us_wd_id: string;
    wtf_address: string;
    wtf_distance: string;
    wtf_id: string;
    wtf_installation_date: string;
    wtf_manufacturer: string;
    wtf_model: string;
    wtf_technical_passport_id: string;
    wtf_type: string;
}

export interface NtisUsedSludgesDAO extends NtisUsedSludgesDAOGen {
}

export interface NtisUsedSludgesDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    us_id: number;
    us_wd_id: number;
    us_wtf_id: number;
}

export interface NtisUserModel extends SprUsersDAO {
    personCodeConfirmed: string;
}

export interface NtisWastewaterDataImportFile {
    cwf_id: number;
    cwf_import_date: Date;
    cwf_status: string;
    cwf_status_date: Date;
    fil_content_type: string;
    fil_key: string;
    fil_name: string;
    fil_size: number;
    fil_status: string;
    fil_status_date: Date;
    org_name: string;
    total_errors: number;
    total_records: number;
}

export interface NtisWastewaterDeliveriesDAO extends NtisWastewaterDeliveriesDAOGen {
}

export interface NtisWastewaterDeliveriesDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    wd_accepted_sewage_quantity: number;
    wd_additional_information_sludge_delivery: string;
    wd_cr_id: number;
    wd_delivered_quantity: number;
    wd_delivered_wastewater_description: string;
    wd_delivery_date: Date;
    wd_description: string;
    wd_id: number;
    wd_ord_id: number;
    wd_org_id: number;
    wd_rejection_reason: string;
    wd_sewage_type: string;
    wd_state: string;
    wd_used_sludge_quantity: number;
    wd_wd_id: number;
    wd_wto_id: number;
}

export interface NtisWastewaterDeliveryEditModel {
    deletedOriginFacilities: NtisSewageOriginFacility[];
    deletedUsedFacilities: string[];
    deliveryInfo: NtisWastewaterDeliveriesDAO;
    originFacilities: NtisSewageOriginFacility[];
    usedSewageFacilities: NtisUsedSewageFacility[];
    wastewaterTreatmentOrg: NtisWastewaterTreatmentOrgDAO;
}

export interface NtisWastewaterFacilityListModel {
    wtf_fl_address: string;
    wtf_fl_latitude: string;
    wtf_fl_longitude: string;
    wtf_id: number;
    wtf_served_objects: string;
    wtf_state: string;
    wtf_type: string;
}

export interface NtisWastewaterTreatmentFaci {
    attachments: SprFile[];
    dischargeWastewaterAddr: AddressSearchResponse;
    facilityLocationAddr: AddressSearchResponse;
    fam_bds: number;
    fam_chds: number;
    fam_description: string;
    fam_float_material: number;
    fam_manufacturer: string;
    fam_model: string;
    fam_nitrogen: number;
    fam_phosphor: number;
    fam_pop_equivalent: number;
    fam_rfc_id: number;
    servedObjects: NtisServedObjectsDAO[];
    servedObjectsAddr: AddressSearchResponse[];
    wtf_ad_id: number;
    wtf_capacity: string;
    wtf_checkout_date: Date;
    wtf_data_source: string;
    wtf_discharge_latitude: number;
    wtf_discharge_longitude: number;
    wtf_discharge_type: string;
    wtf_distance: number;
    wtf_facility_latitude: number;
    wtf_facility_longitude: number;
    wtf_fam_id: number;
    wtf_id: number;
    wtf_identification_number: string;
    wtf_installation_date: Date;
    wtf_manufacturer: string;
    wtf_manufacturer_description: string;
    wtf_model: string;
    wtf_state: string;
    wtf_technical_passport_id: string;
    wtf_type: string;
}

export interface NtisWastewaterTreatmentFaciDAO extends NtisWastewaterTreatmentFaciDAOGen {
}

export interface NtisWastewaterTreatmentFaciDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    wtf_ad_id: number;
    wtf_agg_id: number;
    wtf_capacity: string;
    wtf_checkout_date: Date;
    wtf_data_source: string;
    wtf_deleted: string;
    wtf_discharge_latitude: number;
    wtf_discharge_longitude: number;
    wtf_discharge_type: string;
    wtf_distance: number;
    wtf_facility_latitude: number;
    wtf_facility_longitude: number;
    wtf_facility_municipality_code: string;
    wtf_fam_id: number;
    wtf_id: number;
    wtf_installation_date: Date;
    wtf_manufacturer: string;
    wtf_manufacturer_description: string;
    wtf_model: string;
    wtf_nc_ad_id: number;
    wtf_nc_agg_id: number;
    wtf_nc_capacity: string;
    wtf_nc_checkout_date: Date;
    wtf_nc_data_source: string;
    wtf_nc_deleted: string;
    wtf_nc_discharge_latitude: number;
    wtf_nc_discharge_longitude: number;
    wtf_nc_discharge_type: string;
    wtf_nc_distance: number;
    wtf_nc_facility_latitude: number;
    wtf_nc_facility_longitude: number;
    wtf_nc_facility_municipality_code: string;
    wtf_nc_fam_bds: number;
    wtf_nc_fam_chds: number;
    wtf_nc_fam_description: string;
    wtf_nc_fam_float_material: number;
    wtf_nc_fam_manufacturer: string;
    wtf_nc_fam_model: string;
    wtf_nc_fam_nitrogen: number;
    wtf_nc_fam_phosphor: number;
    wtf_nc_fam_pop_equivalent: number;
    wtf_nc_fam_tech_pass: string;
    wtf_nc_installation_date: Date;
    wtf_nc_manufacturer: string;
    wtf_nc_manufacturer_description: string;
    wtf_nc_model: string;
    wtf_nc_technical_passport_id: string;
    wtf_nc_type: string;
    wtf_state: string;
    wtf_technical_passport_id: string;
    wtf_type: string;
    wtf_waiting_update_confirmation: string;
}

export interface NtisWastewaterTreatmentFacility {
    wtf_address: string;
    wtf_distance: string;
    wtf_id: number;
    wtf_installation_date: Date;
    wtf_manufacturer: string;
    wtf_model: string;
    wtf_org_id: number;
    wtf_technical_passport_id: string;
    wtf_type: string;
    wtf_usr_id: number;
}

export interface NtisWastewaterTreatmentOrgDAO extends NtisWastewaterTreatmentOrgDAOGen {
}

export interface NtisWastewaterTreatmentOrgDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    wto_ad_id: number;
    wto_address: string;
    wto_auto_accept: string;
    wto_domestic_sewage: string;
    wto_id: number;
    wto_industrial_sewage: string;
    wto_is_it_used: string;
    wto_name: string;
    wto_no_notifications: string;
    wto_org_id: number;
    wto_productivity: number;
    wto_sludge: string;
}

export interface NtisWaterManagerFacility {
    address_id: number;
    wto_address: string;
    wto_auto_accept: string;
    wto_domestic_sewage: string;
    wto_id: number;
    wto_industrial_sewage: string;
    wto_is_it_used: string;
    wto_name: string;
    wto_no_notifications: string;
    wto_productivity: number;
    wto_sludge: string;
}

export interface NtisWaterManagerSelectionModel {
    date: Date;
    id: number;
    name: string;
    ord_id: number;
    wtf_id: number;
}

export interface NtisWtfInfo {
    address: string;
    capacity: string;
    distance: string;
    id: number;
    installationDate: string;
    manufacturer: string;
    model: string;
    technicalPassport: string;
    type: string;
    typeClsf: string;
}

export interface NtrOwnerModel {
    organization_id: number;
    owner_code: string;
    owner_exists: string;
    owner_id: number;
    owner_lastname: string;
    owner_name: string;
    owner_organization_name: string;
    owner_type: string;
    person_id: number;
}

export interface OrgDetailsForOrderImport {
    car_exists: number;
    orgId: number;
    orgName: string;
    srvId: number;
    srvTypeClsf: string;
}

export interface OrgUserRequest {
    isSelected: boolean;
    org_id: string;
    ou_date_from: Date;
    ou_date_to: Date;
    ou_id: string;
    ou_org_id: string;
    ou_position: string;
    ou_usr_id: string;
    usr_id: string;
}

export interface OrgUserRoleRequest {
    ou_id: number;
    our_date_from: Date;
    our_date_to: Date;
    our_id: number;
    rol_id: number;
    selected: boolean;
    update: boolean;
    user_id: number;
}

export interface OrganizationRoleRequest {
    isSelected: boolean;
    oar_date_from: Date;
    oar_date_to: Date;
    oar_id: string;
    org_id: string;
    rol_id: string;
}

export interface PredefinedFilter {
    filterCode: string;
    filterContent: string;
    filterName: string;
    id: string;
    userId: string;
}

export interface PredefinedFilterRestDataModel {
    extendedParams: AdvancedSearchParameterStatement[];
    filterDescription: string;
    filterName: string;
    formCode: string;
    globalFilter: boolean;
    predefinedCondition: string;
}

export interface PredefinedFilterStructure {
    extendedParams: AdvancedSearchParameterStatement[];
    predefinedCondition: string;
}

export interface PrototypeBackendRestApiErrorHandler extends S2RestApiErrorHandler {
}

export interface RecordIdentifier {
    actionType?: string;
    id: string;
}

export interface RefCodesClassifierObj {
    rfc_code: string;
    rfc_description: string;
    rfc_domain: string;
    rfc_id: number;
    rfc_meaning: string;
    rfc_order: number;
    rfc_parent_domain: string;
    rfc_parent_domain_code: string;
    rft: RefTranslationsObj[];
}

export interface RefCodesTranslations {
    rfc_code: string;
    rfc_description: string;
    rfc_domain: string;
    rfc_id: number;
    rfc_meaning: string;
    rfc_order: number;
    rfc_parent_domain: string;
    rfc_parent_domain_code: string;
    rft_description: string;
    rft_display_code: string;
    rft_id: number;
    rft_lang: string;
    rft_rfc_id: number;
}

export interface RefTranslationsObj {
    rft_description: string;
    rft_display_code: string;
    rft_id: number;
    rft_lang: string;
    rft_rfc_id: number;
}

export interface RenewPasswordRequest extends Serializable {
    email: string;
    token: string;
}

export interface Request4FormActions {
    availableActionsCnt: string;
    form: Form;
    rol_id: string;
}

export interface Request4FormState {
    disabled_forms: Form4RoleRequest[];
    enabled_forms: Form[];
    rol_id: string;
}

export interface Request4FormsDefinedData {
    formCode: string;
    formId: string;
    language: string;
}

export interface Request4RefCode {
    code: string;
    lang: string;
    parameters: { [index: string]: string };
}

export interface RequestToken {
    processRequestType: SAPProcessRequestType;
    requestType: string;
    token: string;
}

export interface ResearchCriteriaResultsModel {
    code: string;
    display: string;
    norm: number;
    normCompliance: string;
    resId: number;
    result: number;
    rn_id: number;
}

export interface ResearchRequestedCriteriaModel {
    belongs: string;
    code: string;
    display: string;
    isSelected: boolean;
    rn_id: number;
}

export interface RoleFormsActions extends Serializable {
    defaultFormUrl: string;
    roleForms: { [index: string]: FormActions };
    roleId: string;
    roleName: string;
}

export interface RoleRequest {
    roa_assigned_rol_id: string;
    roa_id: string;
    roa_rol_id: string;
    update: boolean;
}

export interface RoleRolesDate {
    roa_date_from: Date;
    roa_date_to: Date;
    roa_id: string;
}

export interface RoleRolesRecord {
    roa_assigned_rol_id: string;
    roa_rol_id: string;
}

export interface S2RestApiErrorHandler extends InitializingBean {
}

export interface Serializable {
}

export interface ServerErrorStackTrace {
    cause: string;
    stackTrace: string;
}

export interface SprApiKeysDAO extends SprApiKeysDAOGen {
}

export interface SprApiKeysDAOGen extends SprBaseDAO {
    api_date_from: Date;
    api_date_to: Date;
    api_id: number;
    api_key: string;
    api_ou_id: number;
    api_type_code: string;
    api_usr_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprAuditableTablesDAO extends SprAuditableTablesDAOGen {
}

export interface SprAuditableTablesDAOGen extends SprBaseDAO {
    aut_archive_table_name: string;
    aut_audit_table_name: string;
    aut_id: number;
    aut_num_of_days_in_archive: number;
    aut_num_of_days_in_audit: number;
    aut_table_name: string;
    aut_table_schema: string;
    aut_trigger_enabled: string;
    aut_trigger_name: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprBackendUserSession extends SprSessionOpenDAO, BackendUserSession {
    daoSession: any;
    defaultRoute: string;
    language: string;
    orgName: string;
    org_name: string;
    per_id: number;
    person_name: string;
    person_surname: string;
    role_name: string;
    role_type: string;
    sessionType: string;
    usr_id: number;
    usr_terms_accepted: string;
    usr_terms_accepted_date: Date;
    usr_valid_from: Date;
    usr_valid_to: Date;
}

export interface SprBackendWebSessionInfo extends WebSessionInfo {
    roleType: string;
    sessionKey: string;
    userType: string;
}

export interface SprBaseDAO extends SprModelBase {
}

export interface SprFile {
    fil_content_type: string;
    fil_key: string;
    fil_name: string;
    fil_size: number;
    fil_status: string;
    fil_status_date: Date;
}

export interface SprFilesDAO extends SprFilesDAOGen {
}

export interface SprFilesDAOGen extends SprBaseDAO {
    fil_content_type: string;
    fil_id: number;
    fil_key: string;
    fil_name: string;
    fil_path: string;
    fil_server: string;
    fil_size: number;
    fil_status: string;
    fil_status_date: Date;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprFormActionsDAO extends SprFormActionsDAOGen {
}

export interface SprFormActionsDAOGen extends SprBaseDAO {
    fra_code: string;
    fra_description: string;
    fra_frm_id: number;
    fra_id: number;
    fra_name: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprFormDataFiltersDAO extends SprFormDataFiltersDAOGen {
}

export interface SprFormDataFiltersDAOGen extends SprBaseDAO {
    fdf_code: string;
    fdf_content: string;
    fdf_date_from: Date;
    fdf_date_to: Date;
    fdf_description: string;
    fdf_frm_id: number;
    fdf_id: number;
    fdf_name: string;
    fdf_rol_id: number;
    fdf_usr_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprFormsDAO extends SprFormsDAOGen {
}

export interface SprFormsDAOGen extends SprBaseDAO {
    frm_code: string;
    frm_description: string;
    frm_id: number;
    frm_name: string;
    frm_uri: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprJobAvailableTemplate {
    id: number;
    name: string;
}

export interface SprJobDefinitionsDAO extends SprJobDefinitionsDAOGen {
}

export interface SprJobDefinitionsDAOGen extends SprBaseDAO {
    jde_action_type: string;
    jde_adjust_to_current_date: string;
    jde_code: string;
    jde_date_from: Date;
    jde_date_to: Date;
    jde_days_in_history: number;
    jde_days_in_request: number;
    jde_default_executer: string;
    jde_default_output_type: string;
    jde_description: string;
    jde_email_tml_id: number;
    jde_execution_parameter: string;
    jde_execution_unit: string;
    jde_hour: number;
    jde_id: number;
    jde_last_action_time: Date;
    jde_minutes: number;
    jde_month_day: number;
    jde_name: string;
    jde_next_action_time: Date;
    jde_ntf_on_completeion: string;
    jde_ntf_tml_id: number;
    jde_ntf_tml_tmt_code: string;
    jde_path: string;
    jde_period: string;
    jde_period_in_minutes: number;
    jde_status: string;
    jde_system: string;
    jde_tml_id: number;
    jde_type: string;
    jde_week_day: number;
    jde_year_day: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprJobRequestArgsDAO extends SprJobRequestArgsDAOGen {
}

export interface SprJobRequestArgsDAOGen extends SprBaseDAO {
    jra_id: number;
    jra_jrq_id: number;
    jra_name: string;
    jra_value: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprJobRequestAuthorsDAO extends SprJobRequestAuthorsDAOGen {
}

export interface SprJobRequestAuthorsDAOGen extends SprBaseDAO {
    jrt_id: number;
    jrt_jrq_id: number;
    jrt_org_id: number;
    jrt_usr_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprJobRequestExecutionsDAO extends SprJobRequestExecutionsDAOGen {
}

export interface SprJobRequestExecutionsDAOGen extends SprBaseDAO {
    jre_err: string;
    jre_event_info: string;
    jre_id: number;
    jre_jrq_id: number;
    jre_name: string;
    jre_time: Date;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprJobRequestsDAO extends SprJobRequestsDAOGen {
}

export interface SprJobRequestsDAOGen extends SprBaseDAO {
    jrq_data: string;
    jrq_end_date: Date;
    jrq_error: string;
    jrq_executer_name: string;
    jrq_fil_id: number;
    jrq_host_created: string;
    jrq_id: number;
    jrq_jde_id: number;
    jrq_lang: string;
    jrq_priority: string;
    jrq_request_time: Date;
    jrq_result_time: Date;
    jrq_result_type: string;
    jrq_start_date: Date;
    jrq_status: string;
    jrq_type: string;
    jrq_usr_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprListIdKeyValue extends Serializable {
    display: string;
    id: number;
    key: string;
}

export interface SprMenuStructuresDAO extends SprMenuStructuresDAOGen {
}

export interface SprMenuStructuresDAOGen extends SprBaseDAO {
    mst_code: string;
    mst_date_from: Date;
    mst_date_to: Date;
    mst_frm_id: number;
    mst_icon: string;
    mst_id: number;
    mst_is_public: string;
    mst_lang: string;
    mst_mst_id: number;
    mst_order: number;
    mst_site: string;
    mst_title: string;
    mst_uri: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprModelBase {
    formActions: FormActions;
}

export interface SprNewsCommentsDAO extends SprNewsCommentsDAOGen {
}

export interface SprNewsCommentsDAOGen extends SprBaseDAO {
    nwc_comment: string;
    nwc_create_date: Date;
    nwc_id: number;
    nwc_nw_id: number;
    nwc_nwc_id: number;
    nwc_usr_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprNewsDAO extends SprNewsDAOGen {
}

export interface SprNewsDAOGen extends SprBaseDAO {
    nw_content_for_search: string;
    nw_date_from: Date;
    nw_date_to: Date;
    nw_id: number;
    nw_lang: string;
    nw_publication_date: Date;
    nw_summary: string;
    nw_text: string;
    nw_title: string;
    nw_type: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprNewsFilesDAO extends SprNewsFilesDAOGen {
}

export interface SprNewsFilesDAOGen extends SprBaseDAO {
    nwf_fil_id: number;
    nwf_id: number;
    nwf_nw_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprNotificationsDAO extends SprNotificationsDAOGen {
}

export interface SprNotificationsDAOGen extends SprBaseDAO {
    ntf_creation_date: Date;
    ntf_date_from: Date;
    ntf_date_to: Date;
    ntf_id: number;
    ntf_mark_as_read_date: Date;
    ntf_message: string;
    ntf_org_id: number;
    ntf_reference: number;
    ntf_rol_id: number;
    ntf_title: string;
    ntf_type: string;
    ntf_usr_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprNotificationsNtisDAO extends SprNotificationsDAO {
    isImportant: Date;
    msgSubject: string;
    refType: string;
}

export interface SprOrgAvailableRolesDAO extends SprOrgAvailableRolesDAOGen {
}

export interface SprOrgAvailableRolesDAOGen extends SprBaseDAO {
    oar_date_from: Date;
    oar_date_to: Date;
    oar_id: number;
    oar_org_id: number;
    oar_rol_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprOrgUserRolesDAO extends SprOrgUserRolesDAOGen {
}

export interface SprOrgUserRolesDAOGen extends SprBaseDAO {
    our_date_from: Date;
    our_date_to: Date;
    our_id: number;
    our_ou_id: number;
    our_rol_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprOrgUsersDAO extends SprOrgUsersDAOGen {
}

export interface SprOrgUsersDAOGen extends SprBaseDAO {
    ou_date_from: Date;
    ou_date_to: Date;
    ou_id: number;
    ou_org_id: number;
    ou_position: string;
    ou_profile_token: string;
    ou_usr_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprOrganizationsDAO extends SprOrganizationsDAOGen {
}

export interface SprOrganizationsDAOGen extends SprBaseDAO {
    org_address: string;
    org_bank_account: string;
    org_bank_name: string;
    org_code: string;
    org_date_from: Date;
    org_date_to: Date;
    org_delegation_person: string;
    org_delegation_person_position: string;
    org_email: string;
    org_house_number: string;
    org_id: number;
    org_name: string;
    org_org_id: number;
    org_phone: string;
    org_region: string;
    org_type: string;
    org_website: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprOrganizationsNtisDAO extends SprOrganizationsDAO {
    adminUsrId: number;
    emailNotifications: boolean;
    facilityInstallerDateFrom: Date;
    loginDefaultRole: string;
    manageOrgRoles: number;
    managerOrgRoles: number;
    municipalityCode: number;
    orgDeregisteredDate: Date;
    orgRegisteredDate: Date;
    orgRejectionReason: string;
    orgState: number;
    orgType: string;
    rcOrgType: number;
    renewFromRcDate: Date;
    website: string;
}

export interface SprPersonsDAO extends SprPersonsDAOGen {
    performSyncWithUser: boolean;
}

export interface SprPersonsDAOGen extends SprBaseDAO {
    per_address_city: string;
    per_address_country_code: string;
    per_address_flat_number: string;
    per_address_house_number: string;
    per_address_post_code: string;
    per_address_street: string;
    per_avatar_fil_id: number;
    per_code: string;
    per_code_exists: string;
    per_confirmation_date: Date;
    per_data_confirmed: string;
    per_date_of_birth: Date;
    per_document_number: string;
    per_document_type: string;
    per_document_valid_until: Date;
    per_email: string;
    per_email_confirmed: string;
    per_id: number;
    per_lrt_resident: string;
    per_name: string;
    per_phone_number: string;
    per_photo_fil_id: number;
    per_surname: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprProcessRequestsDAO extends SprProcessRequestsDAOGen {
}

export interface SprProcessRequestsDAOGen extends SprBaseDAO {
    prq_date_from: Date;
    prq_date_to: Date;
    prq_id: number;
    prq_initiated_by_system: string;
    prq_lang: string;
    prq_reference_id: number;
    prq_token: string;
    prq_type: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprProfile {
    api_id: number;
    api_key: string;
    emailNotifications: boolean;
    linkAccountsAvailable?: boolean;
    per_address_city: string;
    per_address_country_code: string;
    per_address_flat_number: string;
    per_address_house_number: string;
    per_address_post_code: string;
    per_address_street: string;
    per_code: string;
    per_email: string;
    per_email_confirmed: boolean;
    per_name: string;
    per_phone_number: string;
    per_surname: string;
}

export interface SprPropertiesDAO extends SprPropertiesDAOGen {
}

export interface SprPropertiesDAOGen extends SprBaseDAO {
    prp_description: string;
    prp_fil_id: number;
    prp_guid: string;
    prp_id: number;
    prp_install_instance: string;
    prp_name: string;
    prp_type: string;
    prp_value: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprPropertiesModel extends SprPropertiesDAO {
    attachment?: SprFile;
}

export interface SprQuestionAnswersDAO extends SprQuestionAnswersDAOGen {
}

export interface SprQuestionAnswersDAOGen extends SprBaseDAO {
    fac_answer: string;
    fac_code: string;
    fac_content_for_search: string;
    fac_date_from: Date;
    fac_date_to: Date;
    fac_group: string;
    fac_id: number;
    fac_lang: string;
    fac_question: string;
    fac_type: string;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprQuestionFilesDAO extends SprQuestionFilesDAOGen {
}

export interface SprQuestionFilesDAOGen extends SprBaseDAO {
    fcf_fac_id: number;
    fcf_fil_id: number;
    fcf_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprRefCodesDAO extends SprRefCodesDAOGen {
    c01: string;
    c02: string;
    c03: string;
    c04: string;
    c05: string;
    d01: Date;
    d02: Date;
    d03: Date;
    d04: Date;
    d05: Date;
    n01: number;
    n02: number;
    n03: number;
    n04: number;
    n05: number;
}

export interface SprRefCodesDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    rfc_code: string;
    rfc_date_from: Date;
    rfc_date_to: Date;
    rfc_description: string;
    rfc_domain: string;
    rfc_id: number;
    rfc_meaning: string;
    rfc_order: number;
    rfc_parent_domain: string;
    rfc_parent_domain_code: string;
    rfc_ref_code_1: string;
    rfc_ref_code_2: string;
    rfc_ref_code_3: string;
    rfc_ref_code_4: string;
    rfc_ref_code_5: string;
    rfc_rfd_id: number;
}

export interface SprRefDictionariesDAO extends SprRefDictionariesDAOGen {
}

export interface SprRefDictionariesDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    rfd_c1_colname: string;
    rfd_c2_colname: string;
    rfd_c3_colname: string;
    rfd_c4_colname: string;
    rfd_c5_colname: string;
    rfd_code_colname: string;
    rfd_code_length: number;
    rfd_code_type: string;
    rfd_d1_colname: string;
    rfd_d2_colname: string;
    rfd_d3_colname: string;
    rfd_d4_colname: string;
    rfd_d5_colname: string;
    rfd_desc_colname: string;
    rfd_description: string;
    rfd_id: number;
    rfd_n1_colname: string;
    rfd_n2_colname: string;
    rfd_n3_colname: string;
    rfd_n4_colname: string;
    rfd_n5_colname: string;
    rfd_name: string;
    rfd_ref_domain_1: string;
    rfd_ref_domain_2: string;
    rfd_ref_domain_3: string;
    rfd_ref_domain_4: string;
    rfd_ref_domain_5: string;
    rfd_subsystem: string;
    rfd_table_name: string;
}

export interface SprRefTranslationsDAO extends SprRefTranslationsDAOGen {
}

export interface SprRefTranslationsDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    rft_description: string;
    rft_display_code: string;
    rft_id: number;
    rft_lang: string;
    rft_rfc_id: number;
}

export interface SprRoleActionsDAO extends SprRoleActionsDAOGen {
}

export interface SprRoleActionsDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    roa_assigned_rol_id: number;
    roa_date_from: Date;
    roa_date_to: Date;
    roa_default_menu_item: string;
    roa_fra_id: number;
    roa_frm_id: number;
    roa_id: number;
    roa_mst_id: number;
    roa_rol_id: number;
    roa_type: string;
}

export interface SprRoleDisabledActionsDAO extends SprRoleDisabledActionsDAOGen {
}

export interface SprRoleDisabledActionsDAOGen extends SprBaseDAO {
    rda_date_from: Date;
    rda_date_to: Date;
    rda_fra_id: number;
    rda_id: number;
    rda_roa_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprRolesDAO extends SprRolesDAOGen {
}

export interface SprRolesDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    rol_code: string;
    rol_date_from: Date;
    rol_date_to: Date;
    rol_description: string;
    rol_id: number;
    rol_name: string;
    rol_rol_id: number;
    rol_type: string;
}

export interface SprSessionClosedDAO extends SprSessionClosedDAOGen {
}

export interface SprSessionClosedDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    sec_2fa_code: string;
    sec_2fa_confirmed: string;
    sec_browser: string;
    sec_confirmed_release_version: string;
    sec_date_from: Date;
    sec_date_to: Date;
    sec_id: number;
    sec_ip: string;
    sec_key: string;
    sec_language: string;
    sec_last_action_time: Date;
    sec_locked_form_code: string;
    sec_login_method: string;
    sec_login_time: Date;
    sec_logout_cause: string;
    sec_logout_time: Date;
    sec_org_id: number;
    sec_os: string;
    sec_password_reset_req_date: Date;
    sec_per_id: number;
    sec_person_name: string;
    sec_person_surname: string;
    sec_profile_token: string;
    sec_rol_id: number;
    sec_rol_type: string;
    sec_role: string;
    sec_screen_height: number;
    sec_screen_width: number;
    sec_subscripted_month: number;
    sec_terms_accepted: string;
    sec_terms_accepted_date: Date;
    sec_username: string;
    sec_usr_id: number;
    sec_usr_type: string;
}

export interface SprSessionOpenDAO extends SprSessionOpenDAOGen, Serializable {
}

export interface SprSessionOpenDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    ses_2fa_code: string;
    ses_2fa_confirmed: string;
    ses_browser: string;
    ses_confirmed_release_version: string;
    ses_date_from: Date;
    ses_date_to: Date;
    ses_id: number;
    ses_ip: string;
    ses_key: string;
    ses_language: string;
    ses_last_action_time: Date;
    ses_locked_form_code: string;
    ses_login_method: string;
    ses_login_time: Date;
    ses_logout_cause: string;
    ses_logout_time: Date;
    ses_org_id: number;
    ses_os: string;
    ses_password_reset_req_date: Date;
    ses_per_id: number;
    ses_person_name: string;
    ses_person_surname: string;
    ses_profile_token: string;
    ses_rol_id: number;
    ses_rol_type: string;
    ses_role: string;
    ses_screen_height: number;
    ses_screen_width: number;
    ses_subscripted_month: number;
    ses_terms_accepted: string;
    ses_terms_accepted_date: Date;
    ses_username: string;
    ses_usr_id: number;
    ses_usr_type: string;
}

export interface SprTask {
    tas_date_from: Date;
    tas_date_to: Date;
    tas_description: string;
    tas_id: number;
    tas_name: string;
    tas_priority: string;
    tas_reject_reason: string;
    tas_repetitive: string;
    tas_status: string;
    tas_tas_id: number;
    tas_type: string;
    tas_usr_id: number;
    tas_usr_userName: string;
    tas_usr_userSurname: string;
}

export interface SprTaskAssignmentsDAO extends SprTaskAssignmentsDAOGen {
}

export interface SprTaskAssignmentsDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    tat_date_from: Date;
    tat_date_to: Date;
    tat_id: number;
    tat_ins_id: number;
    tat_org_id: number;
    tat_rol_id: number;
    tat_tas_id: number;
    tat_usr_id: number;
}

export interface SprTaskCardBrowsModel {
    data: SprTaskEditModel[];
    pagingParams: Spr_paging_ot;
}

export interface SprTaskCardsBrowseModel {
    data: SprTaskEditModel[];
    paging: Spr_paging_ot;
}

export interface SprTaskEditModel {
    assignees: SprUsersDAO[];
    assigneesIds: string[];
    attachments: SprFile[];
    mainUser: SprUsersDAO;
    progress: number;
    sprTask: SprTask;
    subTasks: SprTaskEditModel[];
}

export interface SprTaskFileKey {
    fil_id: number;
    fil_key: string;
    fil_path: string;
    tfi_id: number;
}

export interface SprTaskFilesDAO extends SprTaskFilesDAOGen {
}

export interface SprTaskFilesDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    tfi_date_from: Date;
    tfi_fil_id: number;
    tfi_id: number;
    tfi_tas_id: number;
    tfi_usr_id: number;
}

export interface SprTasksDAO extends SprTasksDAOGen {
}

export interface SprTasksDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    tas_date_from: Date;
    tas_date_to: Date;
    tas_description: string;
    tas_id: number;
    tas_name: string;
    tas_priority: string;
    tas_reject_reason: string;
    tas_repetitive: string;
    tas_status: string;
    tas_tas_id: number;
    tas_type: string;
    tas_usr_id: number;
}

export interface SprTemplate extends SprTemplatesDAO {
    texts: SprTemplateTextsDAO[];
}

export interface SprTemplateTextsDAO extends SprTemplateTextsDAOGen {
}

export interface SprTemplateTextsDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    tmt_code: string;
    tmt_date_from: Date;
    tmt_date_to: Date;
    tmt_description: string;
    tmt_id: number;
    tmt_lang: string;
    tmt_name: string;
    tmt_text: string;
    tmt_tml_id: number;
}

export interface SprTemplatesDAO extends SprTemplatesDAOGen {
}

export interface SprTemplatesDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    tml_code: string;
    tml_date_from: Date;
    tml_date_to: Date;
    tml_description: string;
    tml_id: number;
    tml_logic_impl: string;
    tml_name: string;
    tml_status: string;
    tml_type: string;
}

export interface SprUserExtIdentificationsDAO extends SprUserExtIdentificationsDAOGen {
}

export interface SprUserExtIdentificationsDAOGen extends SprBaseDAO {
    eid_id: number;
    eid_token: string;
    eid_type: string;
    eid_usr_id: number;
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
}

export interface SprUserRolesDAO extends SprUserRolesDAOGen {
}

export interface SprUserRolesDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    uro_date_from: Date;
    uro_date_to: Date;
    uro_id: number;
    uro_rol_id: number;
    uro_usr_id: number;
}

export interface SprUsersDAO extends SprUsersDAOGen {
    performSyncWithPerson: boolean;
}

export interface SprUsersDAOGen extends SprBaseDAO {
    rec_create_timestamp: Date;
    rec_timestamp: Date;
    rec_userid: string;
    rec_version: number;
    usr_2fa_key: string;
    usr_2fa_key_confirm: string;
    usr_2fa_used: string;
    usr_avatar_fil_id: number;
    usr_confirmed_release_version: string;
    usr_date_from: Date;
    usr_date_to: Date;
    usr_email: string;
    usr_email_confirmed: string;
    usr_id: number;
    usr_language: string;
    usr_lock_date: Date;
    usr_org_id: number;
    usr_password_algorithm: string;
    usr_password_change_date: Date;
    usr_password_hash: string;
    usr_password_history: string;
    usr_password_next_change_date: Date;
    usr_password_reset_req_date: Date;
    usr_per_id: number;
    usr_person_name: string;
    usr_person_surname: string;
    usr_phone_number: string;
    usr_photo_fil_id: number;
    usr_profile_token: string;
    usr_rol_id: number;
    usr_salt: string;
    usr_subscripted_month: number;
    usr_terms_accepted: string;
    usr_terms_accepted_date: Date;
    usr_type: string;
    usr_username: string;
    usr_wrong_login_attempts: number;
}

export interface SprUsersNtisDAO extends SprUsersDAO {
    isViispUser: string;
    personCode: string;
    personCodeConfirmed: string;
}

export interface Spr_paging_ot extends Serializable {
    cnt: number;
    order_clause: string;
    page_size: number;
    skip_rows: number;
    sum_values: Key_values_ot[];
}

export interface SwitchStatus {
    id: number;
    status: string;
}

export interface UserDetails extends Serializable {
    accountNonExpired: boolean;
    accountNonLocked: boolean;
    authorities: GrantedAuthority[];
    credentialsNonExpired: boolean;
    enabled: boolean;
    password: string;
    username: string;
}

export interface UserRole extends Serializable {
    rol_code: string;
    rol_id: string;
    rol_name: string;
}

export interface UserRoleDate {
    uro_date_from: Date;
    uro_date_to: Date;
    uro_id: string;
}

export interface UserRoleRequest {
    rol_id: string;
    update: boolean;
    uro_date_from: Date;
    uro_date_to: Date;
    uro_id: string;
    user_id: number;
}

export interface Version {
    branch: string;
    buildNumber: string;
    buildTimestamp: string;
}

export interface ViispAuthData extends Serializable {
    blocked: boolean;
    companyCode: string;
    companyName: string;
    customData: { [index: string]: any };
    exists: boolean;
    firstName: string;
    governmentEmployeeCode: string;
    lastName: string;
    origin: string;
    personCode: string;
    ticket: string;
}

export interface ViispAuthRequest {
    parameters: { [index: string]: any };
}

export interface ViispAuthResult extends Serializable {
    viispUrlWithTicket: string;
}

export interface ViispMockAuthRequest extends ViispAuthRequest {
    mockAuthData: ViispAuthData;
}

export interface WebSessionInfo {
    defaultRoute: string;
    language: string;
    orgId: number;
    orgName: string;
    personLastName: string;
    personName: string;
    role: string;
    roleCode: string;
    roleId: number;
    roleName: string;
    userName: string;
    usrPasswordChangeToken: string;
    usrTermsAccepted: string;
}

export type SAPProcessRequestType = "NEW_USER_REQUEST" | "CHANGE_PASSWORD_REQUEST" | "CHECK_EMAIL_REQUEST" | "CHANGE_PASSWORD" | "CREATE_PASSWORD_REQUEST";
