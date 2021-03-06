import {SharedModule} from '../../shared/shared.module';
import {NgModule} from '@angular/core';
import {TemplateService} from 'app/components/template/template.service';
import {NgxPermissionsModule} from 'ngx-permissions';
import {CustomerComponent} from 'app/components/+customer/customer.component';
import {customerRouter} from 'app/components/+customer/customer.routes';
import {EditCustomerComponent} from './edit-customer/edit-customer.component';
import {CreateCustomerComponent} from './create-customer/create-customer.component';
import {SearchCustomerComponent} from './search-customer/search-customer.component';

@NgModule({
  declarations: [
    CustomerComponent,
    SearchCustomerComponent,
    CreateCustomerComponent,
    EditCustomerComponent],
  imports: [
    SharedModule,
    NgxPermissionsModule.forChild(),
    customerRouter
  ]
})

export class CustomerModule {
}
