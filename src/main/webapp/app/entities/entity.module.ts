import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { RiscScraperAdmCustomerModule } from './customer/customer.module';
import { RiscScraperAdmSchedulerModule } from './scheduler/scheduler.module';
import { RiscScraperAdmPortalModule } from './portal/portal.module';
import { RiscScraperAdmPortalTypeModule } from './portal-type/portal-type.module';
import { RiscScraperAdmPagesModule } from './pages/pages.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        RiscScraperAdmCustomerModule,
        RiscScraperAdmSchedulerModule,
        RiscScraperAdmPortalModule,
        RiscScraperAdmPortalTypeModule,
        RiscScraperAdmPagesModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class RiscScraperAdmEntityModule {}
