import NotificationStatus from '../enum/NotificationStatus'
import NotificationType from '../enum/NotificationType'
import NotificationRepository from '../repositories/NotificationRepository'
import FriendService from './FriendService'

class NotificationService {
  async sendFriendRequest(receiverId: number, senderId: number) {
    const notification = await NotificationRepository.createNotification(
      NotificationType.FriendRequest,
      receiverId,
      senderId,
      NotificationStatus.Pending,
    )

    return notification
  }

  async acceptFriendRequest(notificationId: number) {
    const notification = await NotificationRepository.updateNotificationStatus(
      notificationId,
      NotificationStatus.Accepted,
    )

    if (notification.senderId) {
      FriendService.addFriend(notification.senderId, notification.receiverId)
      const responseNotification =
        await NotificationRepository.createNotification(
          NotificationType.FriendRequestAccepted,
          notification.senderId,
          notification.receiverId,
        )

      return [notification, responseNotification]
    }
  }
}

export default new NotificationService()
