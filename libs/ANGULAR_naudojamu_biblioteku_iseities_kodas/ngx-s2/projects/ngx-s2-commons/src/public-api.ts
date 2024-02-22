/*
 * Public API Surface of ngx-s2-commons
 */

export * from './lib/ngx-s2-commons.module';
export * from './lib/service/loader.service';
export * from './lib/i18n/lang.util';
export * from './lib/i18n/missing.translation.handler';
export * from './lib/ui/utils';

export * from './lib/message/app.messages';

export * from './lib/error/app.error.handler';
export * from './lib/error/s2error';
export * from './lib/error/servererror';
export * from './lib/error/unauthorized';
export * from './lib/error/server-unavailable-error';

export * from './lib/form/deprecated-base.edit.form';
export * from './lib/form/base.edit.form';
export * from './lib/form/base.browse.form';
export * from './lib/form/common.form.services';

export * from './lib/base.app.component';

export * from './lib/service/interceptor/content.type.interceptor';
export * from './lib/service/interceptor/jwt.interceptor';
export * from './lib/service/interceptor/s2.interceptor';
export * from './lib/service/client-log.service';

export * from './lib/auth/common.auth.service';
export * from './lib/auth/auth.guard';
export * from './lib/auth/auth.util';
export * from './lib/auth/auth.event';
export * from './lib/auth/can-deactivate.guard';

export * from './lib/enums/extended-search';

export { S2Message, S2ViolatedConstraint } from './lib/model/common.api';
export * from './lib/model/extended-search';
export * from './lib/model/spr-paging';

// naudojamas form-field komponentui, kai form-field bus perkeltas į frameworką export'as message.resolver nebus reikalingas
// Jaroslavas 2021-03-25
export * from './lib/ui/field/validation/message.resolver';
