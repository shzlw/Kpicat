import Vue from 'vue'
import Router from 'vue-router'

import PageTable from '@/components/PageTable'
import PageForm from '@/components/PageForm'
import RoleTable from '@/components/RoleTable'
import RoleForm from '@/components/RoleForm'
import UserTable from '@/components/UserTable'
import UserForm from '@/components/UserForm'
import ApiTool from '@/components/ApiTool'
import ConfigForm from '@/components/ConfigForm'
import AccountForm from '@/components/AccountForm'

import Test from '@/components/Test'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: PageTable
    },
    {
      path: '/page',
      component: PageTable
    },
    {
      path: '/page/new',
      component: PageForm
    },
    {
      path: '/page/edit/:id',
      component: PageForm
    },
    {
      path: '/role',
      component: RoleTable
    },
    {
      path: '/role/new',
      component: RoleForm
    },
    {
      path: '/role/edit/:id',
      component: RoleForm
    },
    {
      path: '/user',
      component: UserTable
    },
    {
      path: '/user/new',
      component: UserForm
    },
    {
      path: '/user/edit/:id',
      component: UserForm
    },
    {
      path: '/tool',
      component: ApiTool
    },
    {
      path: '/config',
      component: ConfigForm
    },
    {
      path: '/account',
      component: AccountForm
    }
  ]
})
