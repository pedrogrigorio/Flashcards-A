import { Router } from 'express'
import { AuthMiddleware } from '../middlewares/auth'
import NotificationController from '../controllers/NotificationController'

const notificationRoutes = Router()

notificationRoutes.post(
  '/notifications',
  AuthMiddleware,
  NotificationController.sendFriendRequest,
)

notificationRoutes.put(
  '/notifications/:id/accept',
  AuthMiddleware,
  NotificationController.acceptFriendRequest,
)

export default notificationRoutes
